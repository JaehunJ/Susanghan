package com.oldee.expert.retrofit

import com.oldee.expert.retrofit.request.*
import com.oldee.expert.retrofit.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SusanghanService {
    @GET("/api/v1/expert/versions")
    suspend fun requestVersionInfo(
        @Header("Authorization") authorization: String = "clo",
        @Query("osType") osType:String = "android"
    ): Response<VersionInfoResponse>

    @GET("")
    suspend fun requestFindingId(): Response<BaseResponse>

    @POST("")
    suspend fun requestFindingPW(): Response<BaseResponse>

    @POST()
    suspend fun requestSetPw(): Response<BaseResponse>

    @POST("login")
    suspend fun requestSignIn(
        @Header("Authorization") authorization: String = "clo",
        @Body data: SignInRequest
    ): Response<SignInResponse>

    @PUT("/api/v1/expert/userStatus")
    suspend fun requestUserStatusChange(
        @Header("Authorization") authorization: String,
        @Body data: UserStatusChangeRequest
    ):Response<UserStatusChangeResponse>

    @POST("/api/v1/token/refresh")
    suspend fun requestNewToken(
        @Header("Authorization") authorization: String = "clo",
        @Body data: NewTokenRequest
    ): Response<NewTokenResponse>

    @POST("api/v1/join/expert")
    suspend fun requestSignUp(
        @Body data: SignUpRequest
    ): Response<SignUpResponse>

    @POST("/api/v1/expert/store/certification")
    suspend fun requestStoreConfirm(
        @Header("Authorization") authorization: String = "clo",
        @Body data: StoreConfirmRequest
    ): Response<StoreConfirmResponse>

    @GET("/api/v1/expert/profiles")
    suspend fun requestProfile(
        @Header("Authorization") authorization: String
    ): Response<ProfileResponse>

    @GET("api/v1/expert/orders/list")
    suspend fun requestOrderList(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("period") period: Int
    ): Response<OrderListResponse>

    @GET("/api/v1/expert/orders/details/{id}")
    suspend fun requestOrderDetail(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Response<OrderDetailResponse>

    @GET("/api/v1/expert/orders/counts")
    suspend fun requestOrderCount(
        @Header("Authorization") authorization: String
    ): Response<OrderCountResponse>

    @PUT("/api/v1/expert/orders/status")
    suspend fun requestChangeOrderStatus(
        @Header("Authorization") authorization: String,
        @Body data: OrderStatusUpdateRequest
    ): Response<OrderStatusUpdateResponse>

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
    ): Response<BaseResponseImpl>

    @POST("/api/v1/expert/reforms")
    suspend fun requestAddDesign(
        @Header("Authorization") authorization: String,
        @Body data: DesignPostRequest
    ): Response<BaseResponseImpl>

    @PUT("/api/v1/expert/reforms/{reformId}")
    suspend fun requestModifyDesign(
        @Header("Authorization") authorization: String,
        @Path("reformId") reformId: Int,
        @Body data: DesignPostRequest
    ): Response<BaseResponseImpl>

    @GET("/api/v1/expert/etc/faq")
    suspend fun requestFaq(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Response<FaqResponse>

    @POST("")
    suspend fun requestAddAnswer(): Response<BaseResponse>

    @GET("/api/v1/expert/etc/notices")
    suspend fun requestNotice(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Response<NoticeResponse>

    @GET("/api/v1/expert/image/view")
    suspend fun requestImage(
        @Header("Authorization") authorization: String,
        @Query("imageName") name: String
    ): Response<ResponseBody>

    @Multipart
    @POST("/api/v1/expert/image")
    suspend fun requestPostImage(
        @Header("Authorization") authorization: String,
        @Part files: List<MultipartBody.Part>,
        @Part("imageType") imageType: RequestBody
    ): Response<ImageResponse>

    @GET("/api/v1/expert/etc/couriers")
    suspend fun requestDeliveryList(
        @Header("Authorization") authorization: String = "clo"
    ): Response<DeliveryListResponse>
}