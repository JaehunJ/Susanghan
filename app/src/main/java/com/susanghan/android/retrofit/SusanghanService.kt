package com.susanghan.android.retrofit

import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.request.SignUpRequest
import com.susanghan.android.retrofit.response.BaseResponse
import com.susanghan.android.retrofit.response.OrderListResponse
import com.susanghan.android.retrofit.response.SignInResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.*

interface SusanghanService {
    @GET("")
    fun requestVersionInfo():Flowable<BaseResponse>

    @GET("")
    fun requestFindingId():Flowable<BaseResponse>

    @POST("")
    fun requestFindingPW():Flowable<BaseResponse>

    @POST()
    fun requestSetPw():Flowable<BaseResponse>

//    @Headers("Authorization")
    @POST("login")
    fun requestSignIn(
        @Header("Authorization") authorization:String = "clo",
        @Body data:SignInRequest
    ):Flowable<SignInResponse>

    @POST("api/v1/join/repairman")
    fun requestSignUp(
        @Header("Authorization") authorization: String,
        @Body data:SignUpRequest
    ):Flowable<BaseResponse>

    @POST("")
    fun requestMarketOpen():Flowable<BaseResponse>

    @GET("")
    fun requestUserInfo():Flowable<BaseResponse>

    @GET("api/v1/repairer/orderList")
    fun requestOrderList(
        @Header("Authorization") authorization: String
    ):Flowable<OrderListResponse>

    @GET("")
    fun requestOderDetailStatus():Flowable<BaseResponse>

    @POST("")
    fun requestOderStateChange():Flowable<BaseResponse>

    @POST("")
    fun requestOderDelivery():Flowable<BaseResponse>

    @GET("")
    fun requestOrderDeliveryStatus():Flowable<BaseResponse>

    @GET("")
    fun requestQAList():Flowable<BaseResponse>

    @GET("")
    fun requestQADetail():Flowable<BaseResponse>

    @POST("")
    fun requestAddAnswer():Flowable<BaseResponse>
}