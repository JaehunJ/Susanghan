package com.oldee.expert.ui.more.account.withdraw

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.request.UserStatusChangeRequest
import com.oldee.expert.retrofit.response.UserStatusChangeResponse
import com.oldee.expert.usecase.DoLogoutUseCase
import com.oldee.expert.usecase.SetUserStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val logoutUseCase: DoLogoutUseCase,
    private val postUserStatusUseCase: SetUserStatusUseCase
) :
    BaseViewModel() {

    val status: Int = 0
    val res = MutableLiveData<UserStatusChangeResponse>()

    fun logout() {
        logoutUseCase()
    }

    fun requestUserStatusChange(status: Int, msg: String) {
        remote {
            val data = UserStatusChangeRequest(status, msg)
            val result = postUserStatusUseCase(data)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    res.postValue(it)
                }
            }
        }
    }
}