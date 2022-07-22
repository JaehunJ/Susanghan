package com.oldee.expert.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.ServerRepository
import com.oldee.expert.retrofit.response.VersionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(repository: ServerRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<VersionData?>()

    fun requestVersionInfo() {
        viewModelScope.launch(connectionExceptionHandler) {
            val result = (repository as ServerRepository).requestVersionInfo()

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    data.postValue(it.data)
                }
            }
        }
    }
}