package com.susanghan.android.ui.order

import androidx.lifecycle.MutableLiveData
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.custom.ListLiveData
import com.susanghan.android.repository.OrderListRepository
import com.susanghan.android.retrofit.response.OrderListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(repository: OrderListRepository):BaseViewModel(repository) {
//    val orderList:MutableLiveData

    val orderList = MutableLiveData<List<OrderListResponse.OrderData>>()

    fun requestOrderList(){
        val repo = repository as OrderListRepository
        addDisposable(repo.requestOrderList {
            orderList.postValue(it.data)
        })
    }
}