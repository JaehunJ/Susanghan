package com.susanghan.android.repository

import android.content.SharedPreferences
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.retrofit.SusanghanService
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

    suspend fun requestChangeState() {

    }

    suspend fun requestWriteText() {

    }
}