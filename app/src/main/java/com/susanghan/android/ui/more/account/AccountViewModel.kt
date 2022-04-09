package com.susanghan.android.ui.more.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.SignInRepository
import com.susanghan.android.retrofit.request.UserStatusChangeRequest
import com.susanghan.android.retrofit.response.UserStatusChangeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(repository: SignInRepository) : BaseViewModel(repository) {

    val status:Int = 0
    val res = MutableLiveData<UserStatusChangeResponse>()

    fun requestUserStatusChange(status:Int, msg:String){
        val repo = repository as SignInRepository
        viewModelScope.launch {
            val data = UserStatusChangeRequest(status, msg)
            val result = repo.requestUserStatusChange(data)

            result?.let{
                if(it.errorMessage.isNullOrEmpty()){
                    res.postValue(it)
                }
            }
        }
    }
}