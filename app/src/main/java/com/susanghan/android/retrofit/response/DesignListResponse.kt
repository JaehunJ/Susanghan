package com.susanghan.android.retrofit.response

data class DesignListResponse(
    val count: Int,
    val data: List<DesignData>,
    val status: Int,
    val message: String
) {
    data class DesignData(
        val reformId: Int,
        val reformName: String,
        val imageName: String,
        val heartCnt: Int
    )
}