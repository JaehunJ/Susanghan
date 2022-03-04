package com.susanghan.android.retrofit.response

data class ImageResponse(val count:Int, val data:List<ImageData>, val status: Int,
                         val message: String,
                         val errorMessage: String?, val errorCode: String?,){
    data class ImageData(val imageName:String)
}