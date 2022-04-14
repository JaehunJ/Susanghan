package com.oldee.expert.ui.more

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.response.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(repository: SignInRepository) : BaseViewModel(repository) {
    val profile = MutableLiveData<ProfileResponse.ProfileData?>()
    val versionName = MutableLiveData<String>()
//
//    init {
//        versionName.postValue()
//    }

    fun requestProfile() {
        viewModelScope.launch {
            val result = (repository as SignInRepository).requestUserProfile()

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    profile.postValue(it.data)
                }
            }
        }
    }

    fun getVersionInfo(context: Context){
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        versionName.postValue(info.versionName)
    }
}