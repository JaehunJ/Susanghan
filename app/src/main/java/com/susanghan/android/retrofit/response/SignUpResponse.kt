package com.susanghan.android.retrofit.response

data class SignUpResponse(
    val status: Int,
    val message: String,
    val data: String?,
    override var errorMessage: String?, override var errorCode: String?
) : BaseResponse()
