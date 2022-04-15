package com.oldee.expert.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignUpRepository
import com.oldee.expert.retrofit.RemoteData
import com.oldee.expert.retrofit.request.StoreConfirmRequest
import com.oldee.expert.retrofit.response.StoreConfirmResponse
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

    var phone: String
        get() = (repository as SignUpRepository).phone
        set(value) {
            (repository as SignUpRepository).phone = value
        }

    var email: String
        get() = (repository as SignUpRepository).email
        set(value) {
            (repository as SignUpRepository).email = value
        }

    var code: String
        get() = (repository as SignUpRepository).code
        set(value) {
            (repository as SignUpRepository).code = value
        }

    val confirm = MutableLiveData<StoreConfirmResponse>()

    fun getValidated() =
        name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && code.isNotEmpty()

    fun getInfo() = StoreConfirmRequest(
        name, phone, email, code
//        "정재훈", "01088335697", "wjdthtjfltm@gamil.com", "TESTCODE"
    )

    fun requestConfirm(onError:(RemoteData.ApiError)->Unit) {
        viewModelScope.launch {
            val data = getInfo()
            val result = (repository as SignUpRepository).requestConfirm(data){
                onError.invoke(it)
            }

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    confirm.postValue(it)
                }
            }
        }
    }
}