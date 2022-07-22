package com.oldee.expert.ui.more.account.withdraw

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.request.UserStatusChangeRequest
import com.oldee.expert.retrofit.response.UserStatusChangeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(repository: SignInRepository) :
    BaseViewModel(repository) {

    val status: Int = 0
    val res = MutableLiveData<UserStatusChangeResponse>()

    fun logout(){
        (repository as SignInRepository).logout()
    }

    fun requestUserStatusChange(status: Int, msg: String) {
        val repo = repository as SignInRepository
        viewModelScope.launch(connectionExceptionHandler) {
            val data = UserStatusChangeRequest(status, msg)
            val result = repo.requestUserStatusChange(data)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    res.postValue(it)
                }
            }
        }
    }
}