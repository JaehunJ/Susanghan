package com.susanghan.android.repository

import android.content.SharedPreferences
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.custom.getTextBody
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.request.DesignPostRequest
import com.susanghan.android.retrofit.request.DesignStatusUpdateRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun requestPostImage(list: List<MultipartBody.Part>) =
        call {
            api.requestPostImage(
                getAccessToken(),
                list,
                getTextBody("reform")
            )
        }

    suspend fun requestPostDesign(request: DesignPostRequest) = call {
        api.requestAddDesign(getAccessToken(), request)
    }
}