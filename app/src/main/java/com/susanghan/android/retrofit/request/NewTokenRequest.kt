package com.susanghan.android.retrofit.request

data class NewTokenRequest(
    val userId:String,
    val accessToken:String,
    val refreshToken:String
)