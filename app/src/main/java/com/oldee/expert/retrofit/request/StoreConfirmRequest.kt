package com.oldee.expert.retrofit.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreConfirmRequest(
    val name: String,
    val telNo: String,
    val email: String,
    val recommendCode: String
) : Parcelable
