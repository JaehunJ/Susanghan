package com.oldee.expert.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.response.SignInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(repository: SignInRepository) :
    BaseViewModel(repository) {
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
        val repo = repository as SignInRepository
        CoroutineScope(Dispatchers.IO).launch(connectionExceptionHandler) {
            val result = repo.requestSignIn(id, pw) {
                signInResponse.postValue(null)
            }

            result?.let {
                if (it.errorMessage == null) {
                    repo.setToken(it.data)
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
    }

    fun saveAutoLogin(id: String, pw: String) {
        repository.saveLoginData(id, pw)
    }

    fun loadAutoLogin(): List<String> {
        val list = repository.loadLoginData()

        return list
    }

    fun callErrorFragment(){
        viewModelScope.launch {
            repository.testFragment(true)
            repository.testFragment(false)
        }
    }
}