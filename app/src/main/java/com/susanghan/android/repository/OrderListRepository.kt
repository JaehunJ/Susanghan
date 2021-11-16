package com.susanghan.android.repository

import android.content.SharedPreferences
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.retrofit.response.OrderListResponse
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class OrderListRepository @Inject constructor(
    api: SusanghanService,
    sharedPreferences: SharedPreferences
) : BaseRepository(api, sharedPreferences) {
    fun requestOrderList(onResponse: (OrderListResponse) -> Unit): Disposable {
        return subscribe(api.requestOrderList(getAccessToken()),{
            onResponse(it)
        }){

        }
    }
}