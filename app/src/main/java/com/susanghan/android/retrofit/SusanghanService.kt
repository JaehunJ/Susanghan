package com.susanghan.android.retrofit

import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.request.SignUpRequest
import com.susanghan.android.retrofit.response.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SusanghanService {
    @GET("")
    suspend fun requestVersionInfo(): Response<BaseResponse>

    @GET("")
    suspend fun requestFindingId(): Response<BaseResponse>

    @POST("")
    suspend fun requestFindingPW(): Response<BaseResponse>

    @POST()
    suspend fun requestSetPw(): Response<BaseResponse>

    //    @Headers("Authorization")
    @POST("login")
    suspend fun requestSignIn(
        @Header("Authorization") authorization: String = "clo",
        @Body data: SignInRequest
    ): Response<SignInResponse>

    @POST("api/v1/join/repairman")
    suspend fun requestSignUp(
        @Header("Authorization") authorization: String,
        @Body data: SignUpRequest
    ): Response<BaseResponse>

    @POST("")
    suspend fun requestMarketOpen(): Response<BaseResponse>

    @GET("")
    suspend fun requestUserInfo(): Response<BaseResponse>

    @GET("api/v1/expert/orders/list")
    suspend fun requestOrderList(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("period") period: Int
    ): Response<OrderListResponse>

    @GET("")
    suspend fun requestOderDetailStatus(): Response<BaseResponse>

    @POST("")
    suspend fun requestOderStateChange(): Response<BaseResponse>

    @POST("")
    suspend fun requestOderDelivery(): Response<BaseResponse>

    @GET("")
    suspend fun requestOrderDeliveryStatus(): Response<BaseResponse>

    @GET("api/v1/expert/reforms/list")
    suspend fun requestDesignList(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("status") status: Int
    ): Response<DesignListResponse>

    @GET("api/v1/expert/reforms/details/{reformId}")
    suspend fun requestDesignDetail(
        @Header("Authorization") authorization: String,
        @Path("reformId") reformId: Int
    ): Response<DesignDetailResponse>

    @GET("")
    suspend fun requestQAList(): Response<BaseResponse>

    @GET("")
    suspend fun requestQADetail(): Response<BaseResponse>

    @POST("")
    suspend fun requestAddAnswer(): Response<BaseResponse>

    @GET("/api/v1/expert/image/view")
    suspend fun requestImage(
        @Header("Authorization") authorization: String,
        @Query("imageName") name: String
    ): Response<ResponseBody>
}