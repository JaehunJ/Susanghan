package com.susanghan.android.retrofit.response

data class OrderDetailResponse(
    val count:Int,
    val status:Int,
    val message:String,
    val errorMessage:String?, val errorCode:String?,
    val data:MutableList<OrderDetailSub>
){
    data class OrderDetailSub(
        val classCode: String,
        val code: String,
        val contents: String,
        val imageName: String,
        val images: List<String>,
        val inqDate: String,
        val inqImgName: String,
        val inquiryId: Int,
        val mainCode: String,
        val mainNm: String,
        val orderDetailId: Int,
        val orderId: Int,
        val orderNum: String,
        val orderPrice: Int,
        val orderStatusCode: Int,
        val orderStatusNm: String,
        val paymentDate: String,
        val price: Int,
        val shippingAddress: String,
        val shippingName: String,
        val subNm: String,
        val subNmList: List<String>,
        val surveyId: Int,
        val surveySeq: Int,
        val userPhone: String
    )
}
