package com.susanghan.android.base

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.susanghan.android.data.ACCESS_TOKEN
import com.susanghan.android.data.REFRESH_TOKEN
import com.susanghan.android.data.USER_ID
import com.susanghan.android.retrofit.RemoteData
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.request.NewTokenRequest
import com.susanghan.android.retrofit.response.BaseResponse
import com.susanghan.android.retrofit.response.NewTokenResponse
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    val api: SusanghanService,
    val prefs: SharedPreferences
) {
    private var isLoading = MutableLiveData<Boolean>()

    suspend fun <T> call(apiCall: suspend () -> Response<T>): T? {
        isLoading.postValue(true)
        val response = apiCall.invoke()
        isLoading.postValue(false)

        val result = if (response.isSuccessful) {
            RemoteData.Success(response.body()!!)
        } else {
            RemoteData.Error(IOException(response.message()))
        }

        return when (result) {
            is RemoteData.Success ->
                result.output
            is RemoteData.Error -> {
                Log.e("#debug", result.exception.printStackTrace().toString())
                null
            }
        }
    }

    suspend fun getImageFromServer(url: String): Bitmap? {
        val result = call { api.requestImage(getAccessToken(), url) }

        if (result != null) {
            return BitmapFactory.decodeStream(result.byteStream())
        }

        return null
    }

    suspend fun getNewToken():NewTokenResponse? {
        val id = prefs.getString(USER_ID, "")
        val accessToken = prefs.getString(ACCESS_TOKEN, "")
        val refreshToken = prefs.getString(REFRESH_TOKEN, "")
        val data = NewTokenRequest(id ?: "", accessToken ?: "", refreshToken ?: "")
        val result  = call { api.requestNewToken("clo", data) }

//        result?.let{
//            if(it.errorMessage.isNullOrEmpty()){
//                prefs.edit {
//                    putString(ACCESS_TOKEN, it.data.newAccessToken)
//                    putString(REFRESH_TOKEN, it.data.newRefreshToken)
//                }
//            }
//        }

        return null
    }

    fun getIsLoading() = isLoading
    fun getAccessToken() = "Bearer ${prefs.getString(ACCESS_TOKEN, "")}"
    fun getAccessTokenRaw() = prefs.getString(ACCESS_TOKEN, "")
    fun getRefreshToken() = prefs.getString(REFRESH_TOKEN, "")
}