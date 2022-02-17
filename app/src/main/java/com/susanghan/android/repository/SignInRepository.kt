package com.susanghan.android.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.data.ACCESS_TOKEN
import com.susanghan.android.data.REFRESH_TOKEN
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.response.SignInResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInRepository @Inject constructor(api: SusanghanService, prefs: SharedPreferences) :
    BaseRepository(api, prefs) {

    suspend fun requestSignIn(id: String, pw: String) = call { api.requestSignIn(
        "clo", SignInRequest(id, pw)) }

    fun setToken(data: SignInResponse.SignInData) {
        prefs.edit {
            putString(ACCESS_TOKEN, data.accessToken)
            putString(REFRESH_TOKEN, data.refreshToken)
            commit()
        }
    }

    suspend fun requestSignUp() {

    }

    suspend fun requestUserProfile() {

    }

    suspend fun requestChangePw() {

    }

    suspend fun requestWithdraw() {

    }
}