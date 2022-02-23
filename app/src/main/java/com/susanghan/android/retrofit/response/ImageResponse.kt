package com.susanghan.android.retrofit.response

data class ImageResponse(val count:Int, val data:List<ImageData>, val status:Int, val message:String){
    data class ImageData(val imageName:String)
}