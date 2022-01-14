package com.susanghan.android.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.OrderListRepository
import com.susanghan.android.retrofit.response.OrderListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(repository: OrderListRepository) :
    BaseViewModel(repository) {
//    val orderList:MutableLiveData

    val orderList = MutableLiveData<List<OrderListResponse.OrderData>>()

    fun requestOderList(page: Int, limit: Int, period: Int) {
        val repo = repository as OrderListRepository
        viewModelScope.launch {
            val result = repo.requestOderList(page, limit, period)
            result?.let {
                orderList.postValue(it.data)
            }
        }
    }
}