package com.oldee.expert.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.ServerRepository
import com.oldee.expert.retrofit.response.VersionData
import com.oldee.expert.usecase.GetVersionInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getVersionInfoUseCase: GetVersionInfoUseCase) :
    BaseViewModel() {

    val data = MutableLiveData<VersionData?>()

    fun requestVersionInfo() {
        remote {
            val result = getVersionInfoUseCase()

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    data.postValue(it.data)
                }
            }
        }
    }
}