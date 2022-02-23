package com.susanghan.android.retrofit.request

data class DesignPostRequest(
    val reformName:String,
    val price:Int,
    val contents:String,
    val beforeImageName:String,
    val afterImageName:String,
    val minDay:String,
    val maxDay:Int,
    val imgs:List<ImageData>,
    val items:List<ItemData>
){
    data class ImageData(val imageName:String, val mainImageYn:Int)
    data class ItemData(val itemName:String, val itemCode:String)
}