package com.susanghan.android.retrofit.response

data class NewTokenResponse(
    val count: Int, val status: Int, val data: TokenData,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse() {
    data class TokenData(
        val newAccessToken: String,
        val newRefreshToken: String,
        val refreshToken: String
    )
}
