package com.susanghan.android.retrofit.response

data class SignInResponse(val status:Int, val message:String, val data:SignInData, val count:Int){

    data class SignInData(val access_token:String, val refresh_token:String)
}
