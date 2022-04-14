package com.oldee.expert.retrofit.response

data class OrderCountResponse(
    val count: Int,
    val status: Int,
    val data: OrderCountData,
    val message: String, override var errorMessage: String?, override var errorCode: String?
) :
    BaseResponse() {
    data class OrderCountData(
        val totalCnt: Int,
        val newCnt: Int,
        val progressCnt: Int,
        val completeCnt: Int
    )
}