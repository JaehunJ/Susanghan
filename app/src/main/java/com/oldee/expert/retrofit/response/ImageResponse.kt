package com.oldee.expert.retrofit.response

data class ImageResponse(
    val count: Int, val data: List<ImageData>, val status: Int,
    val message: String,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse() {
    data class ImageData(val imageName: String)
}