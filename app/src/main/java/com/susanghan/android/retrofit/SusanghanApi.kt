package com.susanghan.android.retrofit

import com.susanghan.android.di.retrofitModule
import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.response.BaseResponse
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SusanghanApi(val api:SusanghanService){
    private fun <T> subscribe(
        flowable: Flowable<T>,
        onResponse:(T)->Unit,
        onError:(Throwable)->Unit): Disposable {
        return flowable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onResponse(it)
            }){
                onError(it)
            }
    }

    fun requestSignIn(id:String, pw:String, onResponse:(BaseResponse)->Unit): Disposable{
        return subscribe<BaseResponse>(api.requestSignIn(SignInRequest(id, pw)),onResponse,{

        })
    }
}