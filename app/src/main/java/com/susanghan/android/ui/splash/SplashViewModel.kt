package com.susanghan.android.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.ServerRepository
import com.susanghan.android.retrofit.response.VersionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(repository: ServerRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<VersionData?>()

    fun requestVersionInfo() {
        viewModelScope.launch {
            val result = (repository as ServerRepository).requestVersionInfo()

            result?.let{
                if(it.errorMessage.isNullOrEmpty()){
                    data.postValue(it.data)
                }
            }
        }
    }
}