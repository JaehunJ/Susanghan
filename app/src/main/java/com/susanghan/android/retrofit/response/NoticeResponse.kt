package com.susanghan.android.retrofit.response

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

data class NoticeResponse(
    val count: Int,
    val data: MutableList<NoticeData>,
    val status: Int,
    val message: String,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse() {
    @Keep
    @Parcelize
    data class NoticeData(
        val noticeId: Int,
        val title: String?,
        val contents: String?,
        val creationDate: String
    ) : Parcelable
}
