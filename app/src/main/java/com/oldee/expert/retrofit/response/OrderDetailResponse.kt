package com.oldee.expert.retrofit.response

import com.google.gson.annotations.SerializedName

data class OrderDetailResponse(
    val count: Int,
    val status: Int,
    val message: String,
    override var errorMessage: String?,
    override var errorCode: String?,
    val data: MutableList<OrderDetailSub>
) : BaseResponse() {
    /**
     * TODO
     *
     * @property orderId 주문 id
     * @property orderNum   주문번호
     * @property orderPrice 주문가격
     * @property paymentDate 결제일
     * @property orderDetailId 주문상세 id
     * @property surveySeq
     * @property orderStatusCode 0:주문신청, 1:작업실 배송중, 2:작업중, 3:작업완료, 4:배송중, 5:배송완료,0~1:수선시작, 2:수선완료
     * @property orderStatusNm 주문상태명
     * @property surveyId
     * @property code
     * @property classCode R ㄹㅣ폼
     * @property contents 요청사항
     * @property mainNm 리폼명
     * @property subNm
     * @property imageName 리폼이미지
     * @property mainCode
     * @property price
     * @property shippingAddress 배송지
     * @property userPhone 휴대폰
     * @property shippingName 주문자
     * @property inquiryId
     * @property inqImgName
     * @property inqDate
     * @property subNmList 준비물 리스트
     * @property images
     */
    data class OrderDetailSub(
        @SerializedName("orderId") var orderId: Int? = null,
        @SerializedName("orderNum") var orderNum: String? = null,
        @SerializedName("orderPrice") var orderPrice: Int? = null,
        @SerializedName("paymentDate") var paymentDate: String? = null,
        @SerializedName("orderDetailId") var orderDetailId: Int? = null,
        @SerializedName("surveySeq") var surveySeq: Int? = null,
        @SerializedName("orderStatusCode") var orderStatusCode: Int? = null,
        @SerializedName("orderStatusNm") var orderStatusNm: String? = null,
        @SerializedName("surveyId") var surveyId: Int? = null,
        @SerializedName("code") var code: String? = null,
        @SerializedName("classCode") var classCode: String? = null,
        @SerializedName("contents") var contents: String? = null,
        @SerializedName("mainNm") var mainNm: String? = null,
        @SerializedName("subNm") var subNm: String? = null,
        @SerializedName("imageName") var imageName: String? = null,
        @SerializedName("mainCode") var mainCode: String? = null,
        @SerializedName("price") var price: Int? = null,
        @SerializedName("shippingAddress") var shippingAddress: String? = null,
        @SerializedName("userPhone") var userPhone: String? = null,
        @SerializedName("shippingName") var shippingName: String? = null,
        @SerializedName("inquiryId") var inquiryId: Int? = null,
        @SerializedName("inqImgName") var inqImgName: String? = null,
        @SerializedName("inqDate") var inqDate: String? = null,
        @SerializedName("subNmList") var subNmList: ArrayList<String> = arrayListOf(),
        @SerializedName("images") var images: ArrayList<String> = arrayListOf()
    )
}
