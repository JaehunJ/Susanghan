package com.oldee.expert.retrofit.response

data class DeliveryListResponse(
    val count: Int,
    val status: Int,
    val message: String,
    val data: List<DeliveryItem>,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse() {
    data class DeliveryItem(
        val courierId: Int,
        val courerCode: String,
        val courerName: String
    )
}