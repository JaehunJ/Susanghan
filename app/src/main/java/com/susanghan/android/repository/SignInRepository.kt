package com.susanghan.android.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.data.ACCESS_TOKEN
import com.susanghan.android.data.PREFERENCES_NAME
import com.susanghan.android.data.REFRESH_TOKEN
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.response.SignInResponse
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SignInRepository @Inject constructor(api: SusanghanService) : BaseRepository(api) {
    @Inject val prefs:SharedPreferences

    public fun requestSignIn(id: String, pw: String, onResponse:(SignInResponse)->Unit): Disposable {
        return subscribe<SignInResponse>(super.api.requestSignIn("clo", SignInRequest(id,pw)), onResponse){

        }
    }

    fun setToken(context: Context, data:SignInResponse.SignInData){
        val prefs = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        prefs.edit {
            putString(ACCESS_TOKEN, data.access_token)
            putString(REFRESH_TOKEN, data.refresh_token)
            commit()
        }
    }
}