package com.oldee.expert.usecase

import android.util.Size
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.oldee.expert.R
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.repository.*
import com.oldee.expert.retrofit.RemoteData
import com.oldee.expert.retrofit.request.*
import com.oldee.expert.retrofit.response.ProfileResponse
import com.oldee.expert.retrofit.response.SignInResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class GetVersionInfoUseCase(private val repo: ServerRepository) {
    suspend operator fun invoke() = repo.requestVersionInfo()
}

class GetEditInfoUseCase(private val repo: SignUpRepository) {
    operator fun invoke() = repo.getInfo()
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

class SetSignInNewTokenUseCase(private val repo: BaseRepository) {
    suspend operator fun invoke(data: SignInResponse.SignInData) = repo.setToken(data)
}

class SetLoginDataUseCase(private val repo: BaseRepository) {
    operator fun invoke(id: String, pw: String) = repo.saveLoginData(id, pw)
}

class GetAutoLoginDataUseCase(private val repo: BaseRepository) {
    operator fun invoke() = repo.loadLoginData()
}

class DoLogoutUseCase(private val repo: SignInRepository) {
    operator fun invoke() = repo.logout()
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

class SetProfileUseCase(private val repo: SignInRepository) {
    suspend operator fun invoke(data: ProfileResponse) {
        repo.userInfoRes = data
    }
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

class GetImageUseCase(private val repo: EtcServiceRepository) {
    suspend operator fun invoke(path: String) = repo.requestImageFromServer(path)
}

class SetImageUseCase @Inject constructor(private val getImageUseCase: GetImageUseCase) {
    suspend operator fun invoke(imageView: ImageView, url: String, size: Size? = null) {
        val bitmap = getImageUseCase(url)

        if (bitmap != null) {
            if (size == null) {
                Glide.with(imageView.context).load(bitmap).placeholder(R.drawable.icon_empty_image)
                    .error(R.drawable.icon_empty_image).into(imageView)
            } else {
                Glide.with(imageView.context).load(bitmap).placeholder(R.drawable.icon_empty_image)
                    .override(size.width, size.height)
                    .error(R.drawable.icon_empty_image).into(imageView)
            }
        } else {
            Glide.with(imageView.context).load(R.drawable.icon_empty_image).into(imageView)
        }
    }

    suspend operator fun invoke(imageView: ImageView, url: String, roundDp: Int) {
        val bitmap = getImageUseCase(url)

        if (bitmap != null) {
            Glide.with(imageView.context).load(bitmap)
                .transform(CenterCrop(), RoundedCorners(roundDp)).into(imageView)
        }
    }
}

class SetImageCircleUseCase @Inject constructor(private val getImageUseCase: GetImageUseCase) {
    suspend operator fun invoke(imageView: ImageView, url: String, size: Size? = null) {
        val bitmap = getImageUseCase(url)

        if (bitmap != null) {
            if (size == null) {
                Glide.with(imageView.context).load(bitmap).apply(RequestOptions().circleCrop())
                    .placeholder(R.drawable.icon_empty_image).error(R.mipmap.ic_launcher_round)
                    .into(imageView)
            } else {
                Glide.with(imageView.context).load(bitmap).apply(RequestOptions().circleCrop())
                    .placeholder(R.drawable.icon_empty_image).error(R.mipmap.ic_launcher_round)
                    .override(size.width, size.height)
                    .into(imageView)
            }
        } else {
            Glide.with(imageView.context).load(R.mipmap.ic_launcher_round)
                .apply(RequestOptions().circleCrop())
                .into(imageView)
        }
    }
}

class PostImageUseCase(private val repo: DesignRepository) {
    suspend operator fun invoke(
        onError: ((RemoteData.ApiError) -> Unit)?,
        files: List<MultipartBody.Part>
    ) = repo.requestPostImage(
        onError, files
    )
}

class GetDeliveryListUseCase(private val repo: OrderListRepository) {
    suspend operator fun invoke() = repo.requestDeliveryList()
}

