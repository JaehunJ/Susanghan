package com.oldee.expert.ui.more.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.request.UserStatusChangeRequest
import com.oldee.expert.retrofit.response.ProfileResponse
import com.oldee.expert.usecase.DoLogoutUseCase
import com.oldee.expert.usecase.GetProfileUseCase
import com.oldee.expert.usecase.SetUserStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val logoutUseCase: DoLogoutUseCase,
    private val getUserProfileUseCase: GetProfileUseCase,
    private val postUserStatusUseCase: SetUserStatusUseCase
    ) :
    BaseViewModel() {

    val status = MutableLiveData<Int>()
    val res = MutableLiveData<ProfileResponse.ProfileData?>()
    val updateSuccess = MutableLiveData<Boolean>()

    fun logout() {
        logoutUseCase()
    }

    fun requestUserProfile(reflash:Boolean = false) {
        Log.e("#debug", "request user profile")
        remote {
            val result = getUserProfileUseCase(reflash)

            result?.let {
                if (result.errorMessage.isNullOrEmpty()) {
                    res.postValue(it.data)
                }
            }
        }
    }

    fun requestChangeUserStatus(status: Int, msg: String) {
        remote {
            val data = UserStatusChangeRequest(status, msg)
            val result = postUserStatusUseCase(data)

            result?.let {
                requestUserProfile(true)
            }
        }
    }
}