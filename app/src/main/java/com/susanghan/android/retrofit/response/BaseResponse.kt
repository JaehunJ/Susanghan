package com.susanghan.android.retrofit.response

abstract class BaseResponse {
    abstract var errorMessage: String?
    abstract var errorCode: String?
}

//data class ErrorResponse(val errorMessage:String?, val errorCode:String?)
