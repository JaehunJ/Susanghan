package com.oldee.expert.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.retrofit.RemoteData
import com.oldee.expert.retrofit.request.StoreConfirmRequest
import com.oldee.expert.retrofit.response.StoreConfirmResponse
import com.oldee.expert.usecase.GetEditInfoUseCase
import com.oldee.expert.usecase.GetStoreConfirmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getInfoUseCase: GetEditInfoUseCase,
    private val getStoreConfirmUseCase: GetStoreConfirmUseCase
) :
    BaseViewModel() {

    var name: String
        get() = getInfoUseCase().name
        set(value) {
            getInfoUseCase().name = value
        }

    var phone: String
        get() = getInfoUseCase().phone
        set(value) {
            getInfoUseCase().phone = value
        }

    var email: String
        get() = getInfoUseCase().email
        set(value) {
            getInfoUseCase().email = value
        }

    var code: String
        get() = getInfoUseCase().code
        set(value) {
            getInfoUseCase().code = value
        }

    val confirm = MutableLiveData<StoreConfirmResponse>()

    fun getValidated() =
        name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && code.isNotEmpty()

    fun getInfo() = StoreConfirmRequest(
        name, phone, email, code
//        "정재훈", "01088335697", "wjdthtjfltm@gamil.com", "TESTCODE"
    )

    fun requestConfirm(onError: (RemoteData.ApiError) -> Unit) {
        remote {
            val data = getInfo()
            val result = getStoreConfirmUseCase(data) {
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