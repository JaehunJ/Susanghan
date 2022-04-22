package com.oldee.expert.repository

import android.content.SharedPreferences
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.custom.getTextBody
import com.oldee.expert.retrofit.RemoteData
import com.oldee.expert.retrofit.SusanghanService
import com.oldee.expert.retrofit.request.DesignPostRequest
import com.oldee.expert.retrofit.request.DesignStatusUpdateRequest
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DesignRepository @Inject constructor(
    api: SusanghanService,
    pref: SharedPreferences
) : BaseRepository(api, pref) {

    suspend fun requestDesignList(page: Int, limit: Int, status: Int) =
        call { api.requestDesignList(getAccessToken(), page, limit, status) }

    suspend fun requestDesignDetail(reformId: Int) =
        call { api.requestDesignDetail(getAccessToken(), reformId) }

    suspend fun requestChangeDesignStatus(reformId: Int, status: DesignStatusUpdateRequest) =
        call { api.requestChangeDesignStatus(getAccessToken(), reformId, status) }

    suspend fun requestPostImage(
        onError: ((RemoteData.ApiError) -> Unit)?,
        list: List<MultipartBody.Part>
    ) =
        call(onError) {
            api.requestPostImage(
                getAccessToken(),
                list,
                getTextBody("reform")
            )
        }

    suspend fun requestPostDesign(request: DesignPostRequest) = call {
        api.requestAddDesign(getAccessToken(), request)
    }

    suspend fun requestModifyDesign(id: Int, request: DesignPostRequest) = call {
        api.requestModifyDesign(getAccessToken(), id, request)
    }
}