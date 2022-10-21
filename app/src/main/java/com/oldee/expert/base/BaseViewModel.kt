package com.oldee.expert.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldee.expert.retrofit.NoConnectionInterceptor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val hasError = MutableLiveData<Boolean>()

    val connectionExceptionHandler = CoroutineExceptionHandler { _, e ->
        isLoading.postValue(false)
        if (e is NoConnectionInterceptor.NoConnectivityException) hasError.postValue(true)
    }

    fun remote(useProgressBar: Boolean = true, action: suspend () -> Unit) {
        viewModelScope.launch(connectionExceptionHandler) {
            isLoading.postValue(useProgressBar)

            action()

            isLoading.postValue(false)
        }
    }

    fun postDelay(action: () -> Unit, milisec: Long) {
        viewModelScope.launch {
            delay(milisec)
            action()
        }
    }
}