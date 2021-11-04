package com.susanghan.android.ui.signin

import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.di.retrofitModule
import com.susanghan.android.retrofit.SusanghanApi
import org.koin.core.inject

class SignInViewModel(api:SusanghanApi):BaseViewModel(api) {

    fun requestSignIn(id:String,pw:String){
        addDisposable(api.requestSignIn(id, pw){

        })
    }
}