package com.oldee.expert.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.data.USER_ID
import com.oldee.expert.retrofit.RemoteData
import com.oldee.expert.retrofit.SusanghanService
import com.oldee.expert.retrofit.request.SignInRequest
import com.oldee.expert.retrofit.request.UserStatusChangeRequest
import com.oldee.expert.retrofit.response.ProfileResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInRepository @Inject constructor(api: SusanghanService, prefs: SharedPreferences) :
    BaseRepository(api, prefs) {

    var userInfoRes: ProfileResponse? = null

    suspend fun requestSignIn(id: String, pw: String, onError:(RemoteData.ApiError)->Unit) = call(onError) {
        api.requestSignIn(
            "clo", SignInRequest(id, pw)
        )
    }

    fun setUserId(id: String) {
        prefs.edit {
            putString(USER_ID, id)
        }
    }

    suspend fun requestUserProfile() = if (userInfoRes == null || userInfoRes?.data == null) {
        call {
            api.requestProfile(getAccessToken())
        }
    } else {
        userInfoRes
    }

    suspend fun requestUserStatusChange(data: UserStatusChangeRequest) = call {
        api.requestUserStatusChange(getAccessToken(), data)
    }

    fun logout(){
        userInfoRes = null
        removeLoginData()
    }
}