package com.susanghan.android.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.data.USER_ID
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.response.ProfileResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInRepository @Inject constructor(api: SusanghanService, prefs: SharedPreferences) :
    BaseRepository(api, prefs) {

    var userInfoRes: ProfileResponse? = null

    suspend fun requestSignIn(id: String, pw: String) = call {
        api.requestSignIn(
            "clo", SignInRequest(id, pw)
        )
    }

    fun setUserId(id: String) {
        prefs.edit {
            putString(USER_ID, id)
        }
    }

    suspend fun requestUserProfile() = if (userInfoRes == null) {
        call {
            api.requestProfile(getAccessToken())
        }
    } else {
        userInfoRes
    }
}