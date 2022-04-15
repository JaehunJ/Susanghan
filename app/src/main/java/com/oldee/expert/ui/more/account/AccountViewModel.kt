package com.oldee.expert.ui.more.account

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
class AccountViewModel @Inject constructor(repository: SignInRepository) : BaseViewModel(repository) {

    val status:Int = 0
    val res = MutableLiveData<ProfileResponse.ProfileData?>()

    fun logout(){
        (repository as SignInRepository).logout()
    }

    fun requestUserProfile(){
        viewModelScope.launch {
            val result = (repository as SignInRepository).requestUserProfile()

            result?.let{
                if(result.errorMessage.isNullOrEmpty()){
                    res.postValue(it.data)
                }
            }
        }
    }

    fun requestChangeUserStatus(status:Int, msg:String){
        viewModelScope.launch {
            val data = UserStatusChangeRequest(status, msg)
            val result = (repository as SignInRepository).requestUserStatusChange(data)

            result?.let{
                if(result.errorMessage.isNullOrEmpty()){
                    requestUserProfile()
                }
            }
        }
    }
}