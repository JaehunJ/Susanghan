package com.oldee.expert.ui.more.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.request.UserStatusChangeRequest
import com.oldee.expert.retrofit.response.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(repository: SignInRepository) :
    BaseViewModel(repository) {

    val status = MutableLiveData<Int>()
    val res = MutableLiveData<ProfileResponse.ProfileData?>()
    val updateSuccess = MutableLiveData<Boolean>()

    fun logout() {
        (repository as SignInRepository).logout()
    }

    fun requestUserProfile(reflash:Boolean = false) {
        Log.e("#debug", "request user profile")
        viewModelScope.launch {
            val result = (repository as SignInRepository).requestUserProfile(reflash)

            result?.let {
                if (result.errorMessage.isNullOrEmpty()) {
                    res.postValue(it.data)
                }
            }
        }
    }

    fun requestChangeUserStatus(status: Int, msg: String) {
        viewModelScope.launch {
            val data = UserStatusChangeRequest(status, msg)
            val result = (repository as SignInRepository).requestUserStatusChange(data)

            result?.let {
                requestUserProfile(true)
            }
        }
    }
}