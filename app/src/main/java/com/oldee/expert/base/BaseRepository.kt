package com.oldee.expert.base

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oldee.expert.data.ACCESS_TOKEN
import com.oldee.expert.data.REFRESH_TOKEN
import com.oldee.expert.data.USER_ID
import com.oldee.expert.data.USER_PW
import com.oldee.expert.retrofit.NoConnectionInterceptor
import com.oldee.expert.retrofit.RemoteData
import com.oldee.expert.retrofit.SusanghanService
import com.oldee.expert.retrofit.request.NewTokenRequest
import com.oldee.expert.retrofit.response.BaseResponse
import com.oldee.expert.retrofit.response.NewTokenResponse
import com.oldee.expert.retrofit.response.SignInResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    val api: SusanghanService,
    val prefs: SharedPreferences
) {
    private var isLoading = MutableLiveData<Boolean>()
    private var hasError = MutableLiveData<Boolean>()

    suspend fun <T : BaseResponse> call(
        onError: ((RemoteData.ApiError) -> Unit)? = null,
        forceError: Boolean = false,
        apiCall: suspend () -> Response<T>
    ): T? {
        try{
            hasError.postValue(false)
            isLoading.postValue(true)
            val response = apiCall.invoke()
            isLoading.postValue(false)

            val result = if (response.isSuccessful) {
                if (response.body() != null && !response.body()!!.errorMessage.isNullOrEmpty()) {
                    RemoteData.ApiError(response.body()!!.errorCode, response.body()!!.errorMessage)
                } else if(forceError){
                    RemoteData.Error2("test", "test")
                }else{
                    RemoteData.Success(response.body()!!)
                }
            } else {
                RemoteData.Error2(response.code().toString(), response.message())
            }

//        hasError.postValue(true)
//        return null

            when (result) {
                is RemoteData.Success ->
                    return result.output
                is RemoteData.ApiError -> {
                    //token 에러일 경우
                    if (result.errorCode == "404") {
                        val msgLower = result.errorMessage

                        if (msgLower == null) {
                            onError?.invoke(result)
                            return null
                        }

                        msgLower.let { msg ->
                            val lower = msg.lowercase()

                            if (lower.contains("token")) {
                                val re = getNewToken()

                                return if (re == null) {
                                    Log.e("#debug", "token refresh exception")
                                    null
                                } else {
                                    //토큰 다시 설정하고 다시 콜
                                    setToken(re.data)
                                    return call(onError) { apiCall() }
                                }
                            } else {
                                hasError.postValue(true)
                                onError?.invoke(result)
                                return null
                            }
                        }
                    } else {
                        hasError.postValue(true)
                        onError?.invoke(result)
                        return null
                    }
                }
                is RemoteData.Error -> {
                    Log.e("#debug", result.exception.printStackTrace().toString())
                    hasError.postValue(true)
//                onError?.invoke("")
                    return null
                }
                is RemoteData.Error2->{
                    hasError.postValue(true)
                    return null
                }
            }
        }catch (e:Exception){
            if(e is NoConnectionInterceptor.NoConnectivityException) throw  NoConnectionInterceptor.NoConnectivityException()
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

    suspend fun testFragment(value:Boolean){
        hasError.postValue(value)
    }

    fun getIsLoading() = isLoading
    fun getHasError() = hasError
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
//
//    fun loadPrevSignData():String?{
//        val str = prefs.getString(USER_ID, "")
//        return str
//    }

    fun saveLoginData(id: String, pw: String) {
        prefs.edit {
            putString(USER_ID, id)
            putString(USER_PW, pw)
            commit()
        }
    }

    fun loadLoginData(): List<String> {
        val list = mutableListOf<String>()
        val id = prefs.getString(USER_ID, "") ?: ""
        val pw = prefs.getString(USER_PW, "") ?: ""

        if (id.isNotEmpty())
            list.add(id)

        if (pw.isNotEmpty())
            list.add(pw)

        return list
    }

    fun removeLoginData() {
        prefs.edit {
            remove(USER_ID)
            remove(USER_PW)
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            commit()
        }
    }
}