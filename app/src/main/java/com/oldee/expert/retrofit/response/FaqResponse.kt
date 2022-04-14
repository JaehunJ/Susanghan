package com.oldee.expert.retrofit.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class FaqResponse(
    val count: Int,
    val data: MutableList<FaqData>,
    val status: Int,
    val message: String,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse()

@Parcelize
data class FaqData(
    val noticeId: Int,
    val title: String,
    val contents: String,
    val creationDate: String,
    val faqId: Int
) : Parcelable