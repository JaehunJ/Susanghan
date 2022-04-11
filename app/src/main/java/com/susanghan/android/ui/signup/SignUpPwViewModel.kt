package com.susanghan.android.ui.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.SignUpRepository
import com.susanghan.android.retrofit.request.SignUpRequest
import com.susanghan.android.retrofit.request.StoreConfirmRequest
import com.susanghan.android.retrofit.response.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpPwViewModel @Inject constructor(repository: SignUpRepository) :
    BaseViewModel(repository) {

    var prevData: StoreConfirmRequest? = null

    val pw = MutableLiveData<String>()
    val pwConfirm = MutableLiveData<String>()

    val isCbAll = MutableLiveData<Boolean>()

    val isCb0 = MutableLiveData<Boolean>()
    val isCb1 = MutableLiveData<Boolean>()
    val isCb2 = MutableLiveData<Boolean>()

    val isSuccess = MutableLiveData<SignUpResponse>()

    fun isValidate() = pw.value != null && pwConfirm.value != null && pw.value == pwConfirm.value
    fun isChecked() =
        isCb0.value != null && isCb1.value != null && isCb1.value == true && isCb0.value == true

    fun requestSignUp() {
        viewModelScope.launch {
            Log.e("#debug", "call signup")
            prevData?.let {
                val cb0 = if (isCb0.value == true) {
                    1
                } else {
                    0
                }

                val cb1 = if (isCb1.value == true) {
                    1
                } else {
                    0
                }

                val cb2 = if (isCb2.value == true) {
                    1
                } else {
                    0
                }

                val result = (repository as SignUpRepository).requestSignUp(
                    SignUpRequest(
                        it.email,
                        it.name,
                        pw.value ?: "",
                        it.email,
                        cb1,
                        it.telNo,
                        cb2,
                        cb0
                    )
                )

                result?.let { r ->
                    if (r.errorMessage.isNullOrEmpty()) {
                        isSuccess.postValue(r)
                    }
                }
            }

        }
    }
}