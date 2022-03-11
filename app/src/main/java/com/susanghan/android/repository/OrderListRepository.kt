package com.susanghan.android.repository

import android.content.SharedPreferences
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.retrofit.SusanghanService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderListRepository @Inject constructor(
    api: SusanghanService,
    sharedPreferences: SharedPreferences
) : BaseRepository(api, sharedPreferences) {

    suspend fun requestOderList(page: Int, limit: Int, period: Int) =
        call { api.requestOrderList(getAccessToken(), page, limit, period) }

    suspend fun requestOrderDetail(id: Int) =
        call { api.requestOrderDetail(getAccessToken(), id) }

    suspend fun requestChangeState() {

    }

    suspend fun requestWriteText() {

    }
}