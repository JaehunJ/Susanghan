package com.susanghan.android.repository

import android.content.SharedPreferences
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.request.SignUpRequest
import com.susanghan.android.retrofit.request.StoreConfirmRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpRepository @Inject constructor(api: SusanghanService, prefs: SharedPreferences) :
    BaseRepository(api, prefs) {
    var name: String = ""
    var phone: String = ""
    var email: String = ""
    var code: String = ""
    var pw: String = ""
    var pwConfirm: String = ""
    var marketingYn: Int = 0

    suspend fun requestConfirm(data: StoreConfirmRequest) = call { api.requestStoreConfirm(data) }

    suspend fun requestSignUp(data:SignUpRequest) = call{api.requestSignUp(data)}
}