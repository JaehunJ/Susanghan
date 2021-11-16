package com.susanghan.android.retrofit.response

data class SignInResponse(val status:Int, val message:String, val data:SignInData, val count:Int){

    data class SignInData(val accessToken:String, val refreshToken:String)
}
