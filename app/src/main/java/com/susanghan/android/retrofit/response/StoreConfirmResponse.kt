package com.susanghan.android.retrofit.response

data class StoreConfirmResponse(
    val count: Int,
    val data: String,
    val status: Int,
    val message: String,
    val errorMessage: String?, val errorCode: String?
)
