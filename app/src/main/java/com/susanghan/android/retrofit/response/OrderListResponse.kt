package com.susanghan.android.retrofit.response

data class OrderListResponse(
    val count:Int,
    val data:List<OrderData>,
    val status:Int,
    val message:String
){
    data class OrderData(
        val orderSeq:Int,
        val created_date:String,
        val imagePath:String,
        val resultSeq:Int,
        val subCategory:String,
        val oDeliveryPrice:Int,
        val subCategoryCode:Int,
        val userId:String,
        val oTotalPrice:Int,
        val mainCategory:String,
        val itemCategory:String,
        val oStatus:String,
        val repairerId:String,
        val itemValue:String
    )
}
