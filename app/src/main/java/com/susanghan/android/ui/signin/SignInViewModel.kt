package com.susanghan.android.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.SignInRepository
import com.susanghan.android.retrofit.response.SignInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(repository: SignInRepository) :
    BaseViewModel(repository) {
    val signInResponse = MutableLiveData<SignInResponse>()

    fun requestSignIn(id: String, pw: String) {
        val repo = repository as SignInRepository
        CoroutineScope(Dispatchers.IO).launch{
            val result = repo.requestSignIn(id, pw)

            result?.let {
                if(it.errorMessage == null){
                    repo.setToken(it.data)
                    repo.setUserId(id)

                    signInResponse.postValue(it)
                }
            }
        }
    }
}