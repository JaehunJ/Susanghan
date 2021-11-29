package com.susanghan.android.base

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.susanghan.android.data.ACCESS_TOKEN
import com.susanghan.android.data.REFRESH_TOKEN
import com.susanghan.android.retrofit.SusanghanService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class BaseRepository @Inject constructor(val api:SusanghanService, val prefs:SharedPreferences){
    private var isLoading = MutableLiveData<Boolean>()

    fun <T> subscribe(
        flowable: Flowable<T>,
        onResponse: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        isLoading.postValue(true)
        return flowable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.postValue(false)
                onResponse(it)
            }) {
                isLoading.postValue(false)
                onError(it)
            }
    }

    fun getIsLoading() = isLoading
    fun getAccessToken() = "Bearer ${prefs.getString(ACCESS_TOKEN, "")}"?:""
    fun getRefreshToken() = prefs.getString(REFRESH_TOKEN, "")?:""
}