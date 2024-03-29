package com.oldee.expert.repository

import android.content.SharedPreferences
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.retrofit.RemoteData
import com.oldee.expert.retrofit.SusanghanService
import com.oldee.expert.retrofit.request.SignUpRequest
import com.oldee.expert.retrofit.request.StoreConfirmRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpRepository @Inject constructor(api: SusanghanService, prefs: SharedPreferences) :
    BaseRepository(api, prefs) {
//    var name: String = ""
//    var phone: String = ""
//    var email: String = ""
//    var code: String = ""
//    var pw: String = ""
//    var pwConfirm: String = ""
//    var marketingYn: Int = 0

    private var info = EditInfo()

    fun getInfo() = info


    suspend fun requestConfirm(data: StoreConfirmRequest, onError: (RemoteData.ApiError) -> Unit) =
        call(onError) { api.requestStoreConfirm("clo", data) }

    suspend fun requestSignUp(data: SignUpRequest) = call { api.requestSignUp("clo", data) }

    data class EditInfo(
        var name:String = "",
        var phone:String = "",
        var email:String = "",
        var code:String = "",
        var pw:String = "",
        var pwConfirm:String = "",
        val marketingYn:Int = 0
    )
}