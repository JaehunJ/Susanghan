package com.susanghan.android.base

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.susanghan.android.data.ACCESS_TOKEN
import com.susanghan.android.data.REFRESH_TOKEN
import com.susanghan.android.retrofit.RemoteData
import com.susanghan.android.retrofit.SusanghanService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    val api: SusanghanService,
    val prefs: SharedPreferences
) {
    private var isLoading = MutableLiveData<Boolean>()

    suspend fun <T> call(apiCall:suspend ()->Response<T>):T?{
        isLoading.postValue(true)
        val response = apiCall.invoke()
        isLoading.postValue(false)

        val result = if(response.isSuccessful){
            RemoteData.Success(response.body()!!)
        }else{
            RemoteData.Error(IOException(response.message()))
        }

        return when(result){
            is RemoteData.Success->
                result.output
            is RemoteData.Error->{
                Log.e("#debug", result.exception.toString())
                null
            }
        }
    }

    /**
     * request network
     */
    fun <T> subscribe(
        flowable: Flowable<T>,
        onResponse: (T) -> Unit,
        onError: (Throwable) -> Unit,
        showLoading: Boolean = true
    ): Disposable {
        if (showLoading)
            isLoading.postValue(true)
        return flowable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (showLoading)
                    isLoading.postValue(false)
                onResponse(it)
            }) {
                if (showLoading)
                    isLoading.postValue(false)
                onError(it)
            }
    }

    fun getIsLoading() = isLoading
    fun getAccessToken() = "Bearer ${prefs.getString(ACCESS_TOKEN, "")}"
    fun getAccessTokenRaw() = prefs.getString(ACCESS_TOKEN, "")
    fun getRefreshToken() = prefs.getString(REFRESH_TOKEN, "")
}