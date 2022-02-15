package com.susanghan.android.base

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.susanghan.android.data.ACCESS_TOKEN
import com.susanghan.android.data.REFRESH_TOKEN
import com.susanghan.android.retrofit.RemoteData
import com.susanghan.android.retrofit.SusanghanService
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

    fun getIsLoading() = isLoading
    fun getAccessToken() = "Bearer ${prefs.getString(ACCESS_TOKEN, "")}"
    fun getAccessTokenRaw() = prefs.getString(ACCESS_TOKEN, "")
    fun getRefreshToken() = prefs.getString(REFRESH_TOKEN, "")
}