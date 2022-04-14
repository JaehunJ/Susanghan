package com.oldee.expert.retrofit.request

data class DesignPostRequest(
    var reformName: String,
    var price: Int,
    var contents: String,
    var beforeImageName: String,
    var afterImageName: String,
    var minDay: String,
    var maxDay: Int,
    var imgs: List<ImageData>,
    var items: List<ItemData>
) {
    data class ImageData(var imageName: String, var mainImageYn: Int)
    data class ItemData(var itemName: String, var itemCode: String)
}