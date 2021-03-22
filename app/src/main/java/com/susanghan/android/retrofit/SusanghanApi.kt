package com.susanghan.android.retrofit

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SusanghanApi :SusanghanService{
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
}