package com.oldee.expert.retrofit.request

data class SignUpRequest(
    val userId: String,
    val userName: String,
    val userPassword: String,
    val userEmail: String,
    val userAlertYn: Int,
    val userPhone: String,
    val recommendCode: String,
    val userMarketingYn: Int = 0,
    val userPolicyYn: Int = 0,
    val userOsType: String = "android"
)