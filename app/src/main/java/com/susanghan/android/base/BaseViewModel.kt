package com.susanghan.android.base

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent

abstract class BaseViewModel:ViewModel(), Observable, KoinComponent {

    var compositeDisposable = CompositeDisposable()
    fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}