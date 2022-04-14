package com.oldee.expert.retrofit.request

data class NewTokenRequest(
    val accessToken: String,
    val refreshToken: String
)