package com.susanghan.android.retrofit.response

data class DesignDetailResponse(
    val count:Int,
    val status:Int,
    val message:String,
    val data:DesignDetailData,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse(){
    data class DesignDetailData(
        val reformId:Int,
        val reformName:String,
        val price:Int,
        val contents:String,
        val beforeImageName:String?,
        val afterImageName:String?,
        val minDay:Int,
        val maxDay:Int,
        val status:Int,
        val heartCnt:Int,
        val images:List<String>,
        val items:List<DesignDetailItem>
    )

    data class DesignDetailItem(
        val itemId:Int,
        val itemName:String,
        val itemCode:String
    )
}
