package com.oldee.expert.retrofit.response

data class DesignListResponse(
    val count: Int,
    val data: List<DesignData>,
    val status: Int,
    val message: String,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse() {
    data class DesignData(
        val reformId: Int,
        val reformName: String,
        val imageName: String,
        val heartCnt: Int
    )
}