package com.susanghan.android.retrofit

import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.response.BaseResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SusanghanApi{


    private fun <T> subscribe(
        flowable: Flowable<T>,
        onResponse: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        return flowable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onResponse(it)
            }) {
                onError(it)
            }
    }

//    @Provides
//    fun requestSignIn(id: String, pw: String, onResponse: (BaseResponse) -> Unit): Disposable {
//        return subscribe<BaseResponse>(api.requestSignIn(SignInRequest(id, pw)), onResponse, {
//
//        })
//    }
}