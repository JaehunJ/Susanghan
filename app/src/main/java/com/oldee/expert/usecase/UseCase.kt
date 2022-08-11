package com.oldee.expert.usecase

import com.oldee.expert.base.BaseRepository
import com.oldee.expert.repository.*
import com.oldee.expert.retrofit.RemoteData
import com.oldee.expert.retrofit.request.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class GetVersionInfoUseCase(private val repo: ServerRepository) {
    suspend operator fun invoke() = repo.requestVersionInfo()
}

class PostSignInUseCase(private val repo: SignInRepository) {
    suspend operator fun invoke(data: SignInRequest, onError: (RemoteData.ApiError) -> Unit) =
        repo.requestSignIn(data, onError)

    suspend operator fun invoke(id: String, pw: String, onError: (RemoteData.ApiError) -> Unit) =
        repo.requestSignIn(id, pw, onError)
}

class SetUserStatusUseCase(private val repo: SignInRepository) {
    suspend operator fun invoke(data: UserStatusChangeRequest) = repo.requestUserStatusChange(data)
}

class GetNewTokenUseCase(private val repo: BaseRepository) {
    suspend operator fun invoke() = repo.getNewToken()
}

class PostSignUpUseCase(private val repo: SignUpRepository) {
    suspend operator fun invoke(data: SignUpRequest) = repo.requestSignUp(data)
}

class GetStoreConfirmUseCase(private val repo: SignUpRepository) {
    suspend operator fun invoke(data: StoreConfirmRequest, onError: (RemoteData.ApiError) -> Unit) =
        repo.requestConfirm(data, onError)
}

class GetProfileUseCase(private val repo: SignInRepository) {
    suspend operator fun invoke(refresh: Boolean = false) = repo.requestUserProfile(refresh)
}

class GetOrderListUseCase(private val repo: OrderListRepository) {
    suspend operator fun invoke(page: Int, limit: Int, period: Int) =
        repo.requestOderList(page, limit, period)
}

class GetOrderDetailUseCase(private val repo: OrderListRepository) {
    suspend operator fun invoke(id: Int) = repo.requestOrderDetail(id)
}

class GetOrderCountUseCase(private val repo: OrderListRepository) {
    suspend operator fun invoke() = repo.requestOrderCount()
}

class PostOrderStatusChangeUseCase(private val repo: OrderListRepository) {
    suspend operator fun invoke(data: OrderStatusUpdateRequest) =
        repo.requestChangeOrderStatus(data)
}

class GetDesignListUseCase(private val repo: DesignRepository) {
    suspend operator fun invoke(page: Int, limit: Int, status: Int) =
        repo.requestDesignList(page, limit, status)
}

class GetDesignDetailUseCase(private val repo: DesignRepository) {
    suspend operator fun invoke(reformId: Int) = repo.requestDesignDetail(reformId)
}

class PostDesignStatusChangeUseCase(private val repo: DesignRepository) {
    suspend operator fun invoke(reformId: Int, data: DesignStatusUpdateRequest) =
        repo.requestChangeDesignStatus(reformId, data)
}

class PostDesignAddUseCase(private val repo: DesignRepository) {
    suspend operator fun invoke(data: DesignPostRequest) = repo.requestPostDesign(data)
}

class PostDesignModifyUseCase(private val repo: DesignRepository) {
    suspend operator fun invoke(reformId: Int, data: DesignPostRequest) =
        repo.requestModifyDesign(reformId, data)
}

class GetFaqUseCase(private val repo: EtcServiceRepository) {
    suspend operator fun invoke(page: Int, limit: Int) = repo.requestFaq(page, limit)
}

class GetNoticeUseCase(private val repo: EtcServiceRepository) {
    suspend operator fun invoke(page: Int) = repo.requestNotice(page)
}

class GetImageUseCase(private val repo: BaseRepository) {
    suspend operator fun invoke(path: String) = repo.getImageFromServer(path)
}

class PostImageUseCase(private val repo: DesignRepository) {
    suspend operator fun invoke(
        onError: ((RemoteData.ApiError) -> Unit)?,
        files: List<MultipartBody.Part>
    ) = repo.requestPostImage(
        onError, files
    )
}

class GetDeliveryListUseCase(private val repo:OrderListRepository){
    suspend operator fun invoke() = repo.requestDeliveryList()
}

