package com.susanghan.android.ui.more.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.SignInRepository
import com.susanghan.android.retrofit.request.UserStatusChangeRequest
import com.susanghan.android.retrofit.response.ProfileResponse
import com.susanghan.android.retrofit.response.UserStatusChangeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(repository: SignInRepository) : BaseViewModel(repository) {

    val status:Int = 0
    val res = MutableLiveData<ProfileResponse.ProfileData>()

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