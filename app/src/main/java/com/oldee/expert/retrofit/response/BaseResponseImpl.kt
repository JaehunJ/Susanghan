package com.oldee.expert.retrofit.response

data class BaseResponseImpl(
    val count: Int,
    val status: Int,
    val message: String,
    val data: Any?,
    override var errorMessage: String?, override var errorCode: String?):BaseResponse()
