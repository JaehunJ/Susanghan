package com.oldee.expert.retrofit.response

data class ProfileResponse(
    val count: Int,
    val status: Int,
    val message: String,
    val data: ProfileData?,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse() {
    data class ProfileData(
        val userId: String,
        val userEmail: String,
        val userName: String,
        val storeName: String,
        val userPhone: String,
        val userUuid: Int,
        val userStatus: Int,
        val profileImg:String?,
        val reason: String?
    )
}
