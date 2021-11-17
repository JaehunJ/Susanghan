package com.susanghan.android.repository

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.data.ACCESS_TOKEN
import com.susanghan.android.data.PREFERENCES_NAME
import com.susanghan.android.data.REFRESH_TOKEN
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.response.SignInResponse
import io.reactivex.disposables.Disposable
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInRepository @Inject constructor(api: SusanghanService, prefs:SharedPreferences) : BaseRepository(api, prefs) {

    fun requestSignIn(id: String, pw: String, onResponse:(SignInResponse)->Unit): Disposable {
        return subscribe(super.api.requestSignIn("clo", SignInRequest(id,pw)), {
            if(it.status == 200){
                onResponse.invoke(it)
                setToken(it.data)

                val access = prefs.getString(ACCESS_TOKEN,"")
                val refresh = prefs.getString(REFRESH_TOKEN,"")

                Log.e("okhttp", "${access?:"null"}, ${refresh?:"null"}")
            }
        }){

        }
    }

    fun setToken(data:SignInResponse.SignInData){
        prefs.edit {
            putString(ACCESS_TOKEN, data.accessToken)
            putString(REFRESH_TOKEN, data.refreshToken)
            commit()
        }
    }
}