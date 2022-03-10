package com.susanghan.android.retrofit.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreConfirmRequest(
    val name: String,
    val telNo: String,
    val email: String,
    val recommendCode: String
) : Parcelable
