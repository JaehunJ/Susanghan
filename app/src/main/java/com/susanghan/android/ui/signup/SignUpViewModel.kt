package com.susanghan.android.ui.signup

import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.SignUpRepository
import com.susanghan.android.retrofit.request.StoreConfirmRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(repository: SignUpRepository) :
    BaseViewModel(repository) {

    var name: String
        get() = (repository as SignUpRepository).name
        set(value) {
            (repository as SignUpRepository).name = value
        }

    var phone:String
    get() = (repository as SignUpRepository).phone
    set(value) {
        (repository as SignUpRepository).phone = value
    }

    var email:String
    get() = (repository as SignUpRepository).email
    set(value){
        (repository as SignUpRepository).email = email
    }

    var code:String
    get() = (repository as SignUpRepository).code
    set(value){
        (repository as SignUpRepository).code = value
    }

    fun getValidated() = name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && code.isNotEmpty()

    fun requestConfirm(){
        viewModelScope.launch {
            val data = StoreConfirmRequest(
                name, phone, email, code
            )
            (repository as SignUpRepository).requestConfirm(data)
        }
    }
}