package com.susanghan.android.retrofit.response

data class OrderListResponse(
    val count:Int,
    val data:List<OrderData>,
    val status:Int,
    val message:String,
    val errorMessage:String?, val errorCode:String?,
){
    data class OrderData(
        val paymentDate:String,
        val orderNum:String,
        val orderStatusNm:String,
        val orderPrice:Int,
        val orderList:List<OrderSubData>,
    )

    data class OrderSubData(
        val mainNm:String,
        val mainCode:String,
        val imageName:String,
        val classCode:String,
        val subNm:String
    )
}
