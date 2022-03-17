package com.susanghan.android.retrofit.response

data class OrderListResponse(
    val count:Int,
    val data:List<OrderData>,
    val status:Int,
    val message:String,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse(){
    data class OrderData(
        val orderId:Int,
        val orderNum:String,
        val orderPrice:Int,
        val paymentDate:String,
        val orderDetailId:Int,
        val orderStatusCode:Int,
        val orderStatusNm:String,
        val categoryCode:String,
        val classCode:String,
        val orderList:List<OrderSubData>,
    )

    data class OrderSubData(
        val orderId:Int,
        val orderNum:String,
        val orderPrice:Int,
        val orderDetailId: Int,
        val surveySeq:Int,
        val orderStatusCode:Int,
        val orderStatusNm: String,
        val surveyId:Int,
        val code:String,
        val classCode:String,
        val contents:String,
        val mainNm:String,
        val mainCode:String,
        val imageName:String,
        val subNm:String
    )
}
