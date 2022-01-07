package com.susanghan.android.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Job

abstract class BaseViewModel(var repository: BaseRepository) : ViewModel() {

    val job : Job? = null

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

//    var compositeDisposable = CompositeDisposable()
//    fun addDisposable(disposable: Disposable) {
//        compositeDisposable.add(disposable)
//    }
//
//    override fun onCleared() {
//        compositeDisposable.dispose()
//        compositeDisposable.clear()
//        super.onCleared()
//    }

//    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }
//    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//        callbacks.add(callback)
//    }
//
//    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//        callbacks.remove(callback)
//    }

    fun isLoading() = repository.getIsLoading()
}