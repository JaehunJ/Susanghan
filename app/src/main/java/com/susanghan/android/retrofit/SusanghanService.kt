package com.susanghan.android.retrofit

import com.susanghan.android.retrofit.request.*
import com.susanghan.android.retrofit.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

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

    @POST("api/v1/join/expert")
    suspend fun requestSignUp(
        @Header("Authorization") authorization: String,
        @Body data: SignUpRequest
    ): Response<BaseResponse>

    @POST("/api/v1/expert/store/certification")
    suspend fun requestStoreConfirm(
        @Body data:StoreConfirmRequest
    ):Response<BaseResponse>


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

    @PUT("/api/v1/expert/reforms/{reformId}/status")
    suspend fun requestChangeDesignStatus(
        @Header("Authorization") authorization: String,
        @Path("reformId") reformId: Int,
        @Body data: DesignStatusUpdateRequest
    ): Response<BaseResponse>

    @POST("/api/v1/expert/reforms")
    suspend fun requestAddDesign(
        @Header("Authorization") authorization: String,
        @Body data: DesignPostRequest
    ): Response<BaseResponse>

    @PUT("/api/v1/expert/reforms/{reformId}")
    suspend fun requestModifyDesign(
        @Header("Authorization") authorization: String,
        @Path("reformId") reformId: Int,
        @Body data: DesignPostRequest
    ): Response<BaseResponse>

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

    @Multipart
    @POST("/api/v1/expert/image")
    suspend fun requestPostImage(
        @Header("Authorization") authorization: String,
//        @PartMap part:Map<String, RequestBody>
        @Part files: List<MultipartBody.Part>,
        @Part ("imageType") imageType: RequestBody
    ): Response<ImageResponse>
}