package com.susanghan.android.base

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.susanghan.android.data.ACCESS_TOKEN
import com.susanghan.android.data.REFRESH_TOKEN
import com.susanghan.android.retrofit.RemoteData
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.request.NewTokenRequest
import com.susanghan.android.retrofit.response.BaseResponse
import com.susanghan.android.retrofit.response.NewTokenResponse
import com.susanghan.android.retrofit.response.SignInResponse
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    val api: SusanghanService,
    val prefs: SharedPreferences
) {
    private var isLoading = MutableLiveData<Boolean>()

    suspend fun <T : BaseResponse> call(
        onError: (() -> Unit)? = null,
        apiCall: suspend () -> Response<T>
    ): T? {
        isLoading.postValue(true)
        val response = apiCall.invoke()
        isLoading.postValue(false)

        val result = if (response.isSuccessful) {
            RemoteData.Success(response.body()!!)
        } else if (response.body() != null && !response.body()!!.errorMessage.isNullOrEmpty()) {
            RemoteData.ApiError(response.body()!!.errorCode, response.body()!!.errorMessage)
        } else {
            RemoteData.Error(IOException(response.message()))
        }

        when (result) {
            is RemoteData.Success ->
                return result.output
            is RemoteData.ApiError -> {
                //token 에러일 경우
                if (result.errorCode == "404") {
                    val re = getNewToken()

                    return if (re == null) {
                        Log.e("#debug", "token refresh exception")
                        null
                    } else {
                        //토큰 다시 설정하고 다시 콜
                        setToken(re.data)
                        call { apiCall() }
                    }
                } else {
                    onError?.invoke()
                }
            }
            is RemoteData.Error -> {
                Log.e("#debug", result.exception.printStackTrace().toString())
                return null
            }
        }

        return null
    }

    suspend fun getImageFromServer(url: String): Bitmap? {
        val result = api.requestImage(getAccessToken(), url).body()

        if (result != null) {
            return BitmapFactory.decodeStream(result.byteStream())
        }

        return null
    }

    suspend fun getNewToken(): NewTokenResponse? {
        val accessToken = getAccessTokenRaw()
        val refreshToken = getRefreshToken()
        val data = NewTokenRequest(accessToken ?: "", refreshToken ?: "")
        val result = api.requestNewToken("clo", data)

        return result.body()
    }

    fun getIsLoading() = isLoading
    fun getAccessToken() = "Bearer ${prefs.getString(ACCESS_TOKEN, "")}"
    private fun getAccessTokenRaw() = prefs.getString(ACCESS_TOKEN, "")
    private fun getRefreshToken() = prefs.getString(REFRESH_TOKEN, "")

    fun setToken(data: SignInResponse.SignInData) {
        prefs.edit {
            putString(ACCESS_TOKEN, data.accessToken)
            putString(REFRESH_TOKEN, data.refreshToken)
            commit()
        }
    }

    fun setToken(data: NewTokenResponse.TokenData) {
        prefs.edit {
            putString(ACCESS_TOKEN, data.newAccessToken)
            putString(REFRESH_TOKEN, data.newRefreshToken)
            commit()
        }
    }
}