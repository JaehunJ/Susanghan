package com.oldee.expert.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.retrofit.response.SignInResponse
import com.oldee.expert.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: PostSignInUseCase,
    private val setTokenUseCase: SetSignInNewTokenUseCase,
    private val setAutoLoginData: SetLoginDataUseCase,
    private val getAutoLoginDataUseCase: GetAutoLoginDataUseCase
) :
    BaseViewModel() {
    val signInResponse = MutableLiveData<SignInResponse?>()

    val isCbAuto = MutableLiveData<Boolean>()
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    init {
        isCbAuto.postValue(true)
    }

    fun checkAutoLogin(): Boolean {
        val list = loadAutoLogin()

        return list.isNotEmpty()
    }

    fun requestSignIn(id: String, pw: String) {
        remote {
            val result = signInUseCase(id, pw) {
                signInResponse.postValue(null)
            }

            result?.let {
                if (it.errorMessage == null) {
                    setTokenUseCase(it.data)
//                    repo.setUserId(id)

                    signInResponse.postValue(it)
                    if (it.message == "success") {
//                        saveAutoLoginCheck(isCbAuto.value?:false)
                        if (isCbAuto.value == true) {
                            saveAutoLogin(id, pw)
                        }
                    }
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch(connectionExceptionHandler) {

        }
    }

    fun saveAutoLogin(id: String, pw: String) {
        setAutoLoginData(id, pw)
    }

    fun loadAutoLogin(): List<String> {
        val list = getAutoLoginDataUseCase()

        return list
    }
}