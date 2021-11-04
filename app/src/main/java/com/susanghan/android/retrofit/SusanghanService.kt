package com.susanghan.android.retrofit

import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.response.BaseResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SusanghanService {
    @GET("")
    fun requestVersionInfo():Flowable<BaseResponse>

    @GET("")
    fun requestFindingId():Flowable<BaseResponse>

    @POST("")
    fun requestFindingPW():Flowable<BaseResponse>

    @POST()
    fun requestSetPw():Flowable<BaseResponse>

    @POST("login")
    fun requestSignIn(
        @Body data:SignInRequest
    ):Flowable<BaseResponse>

    @POST("")
    fun requestSignUp():Flowable<BaseResponse>

    @POST("")
    fun requestMarketOpen():Flowable<BaseResponse>

    @GET("")
    fun requestUserInfo():Flowable<BaseResponse>

    @GET("")
    fun requestOrderList():Flowable<BaseResponse>

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