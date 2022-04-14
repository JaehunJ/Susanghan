package com.oldee.expert.repository

import android.content.SharedPreferences
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.retrofit.SusanghanService
import com.oldee.expert.retrofit.request.OrderStatusUpdateRequest
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

    suspend fun requestOrderCount() =
        call { api.requestOrderCount(getAccessToken()) }

    suspend fun requestDeliveryList() =
        call { api.requestDeliveryList() }

    suspend fun requestChangeOrderStatus(data:OrderStatusUpdateRequest) =
        call{api.requestChangeOrderStatus(getAccessToken(), data)}
}