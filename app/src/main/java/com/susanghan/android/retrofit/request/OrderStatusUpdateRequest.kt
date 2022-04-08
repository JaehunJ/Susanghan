package com.susanghan.android.retrofit.request

data class OrderStatusUpdateRequest(
    val orderDetailIds:List<Int>,
    val status:Int,
    val courierCode:String,
    val invoiceNumber:String
)