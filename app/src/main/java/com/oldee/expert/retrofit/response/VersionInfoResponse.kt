package com.oldee.expert.retrofit.response

data class VersionInfoResponse(
    val count:Int,
    val status:Int,
    val message:String,
    val data:VersionData,
    override var errorMessage: String?,
    override var errorCode: String?
) :BaseResponse()

data class VersionData(
    val versionId:Int,
    val versionCode:String,
    val osType:String,
    val appStatus:Int,
    val title:String?,
    val contents:String?,
    val creationDate:String
)
