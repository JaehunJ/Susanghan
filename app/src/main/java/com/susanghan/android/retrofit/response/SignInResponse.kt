package com.susanghan.android.retrofit.response

data class SignInResponse(
    val status: Int,
    val message: String,
    val data: SignInData,
    val count: Int,
    val errorMessage: String?,
    val errorCode: String?,
) {

    data class SignInData(val accessToken: String, val refreshToken: String)
}
