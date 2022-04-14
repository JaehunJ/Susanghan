package com.oldee.expert.retrofit.response

data class UserStatusChangeResponse(
    val count: Int,
    val data: String,
    val status: Int,
    val message: String,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse()
