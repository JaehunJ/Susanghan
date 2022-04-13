package com.susanghan.android.ui.signin

import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
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
    val signInResponse = MutableLiveData<SignInResponse?>()

    val isCbAuto = MutableLiveData<Boolean>()
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    fun checkAutoLogin(){
        val auto = loadAutoLoginCheck()

        isCbAuto.postValue(auto)

        if(auto){
            val list = loadAutoLogin()
            if(list[0].isNotEmpty() && list[1].isNotEmpty()){
                requestSignIn(list[0], list[1])
            }
        }
    }


    fun requestSignIn(id: String, pw: String) {
        val repo = repository as SignInRepository
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.requestSignIn(id, pw){
                signInResponse.postValue(null)
            }

            result?.let {
                if (it.errorMessage == null) {
                    repo.setToken(it.data)
//                    repo.setUserId(id)

                    signInResponse.postValue(it)
                    if(it.message == "success"){
                        saveAutoLoginCheck(isCbAuto.value?:false)
                        if(isCbAuto.value == true){
                            saveAutoLogin(id, pw)
                        }
                    }
                }
            }
        }
    }
//
//    fun loadPrevInfo():String?{
//        return repository.loadPrevSignData()
//    }

    fun saveAutoLoginCheck(v:Boolean){
        repository.prefs.edit {
            putBoolean("enable_auto_login", v)
        }
    }

    fun loadAutoLoginCheck():Boolean{
        val v = repository.prefs.getBoolean("enable_auto_login", false)

        return v
    }

    fun saveAutoLogin(id:String, pw:String){
        repository.saveLoginData(id,pw)
    }

    fun loadAutoLogin():List<String>{
        val list = repository.loadLoginData()

        return list
    }
}