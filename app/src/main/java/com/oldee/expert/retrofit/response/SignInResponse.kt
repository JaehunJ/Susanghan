package com.oldee.expert.retrofit.response

data class  SignInResponse(
    val status: Int,
    val message: String,
    val data: SignInData,
    val count: Int,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse() {

    data class SignInData(val accessToken: String, val refreshToken: String)
}
