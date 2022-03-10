package com.susanghan.android.retrofit.response

data class ProfileResponse(
    val count: Int,
    val status: Int,
    val message: String,
    val data: ProfileData,
    val errorMessage: String?,
    val errorCode: String?
) {
    data class ProfileData(
        val userId: String,
        val userEmail: String,
        val userName: String,
        val storeName: String,
        val userPhone: String,
        val userUuid: Int,
        val userStatus: Int,
        val reason: String?
    )
}
