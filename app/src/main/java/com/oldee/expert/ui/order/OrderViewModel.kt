package com.oldee.expert.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.OrderListRepository
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.response.OrderCountResponse
import com.oldee.expert.retrofit.response.OrderListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    repository: OrderListRepository,
    private val signRepo: SignInRepository
) :
    BaseViewModel(repository) {

    val orderList = MutableLiveData<MutableList<OrderListResponse.OrderData>>()
    val orderCount = MutableLiveData<OrderCountResponse.OrderCountData>()

    var page = 0
    var period = 0

    fun requestOrderCount() {
        val repo = repository as OrderListRepository
        viewModelScope.launch {
            val result = repo.requestOrderCount()

            result?.let {
                if (result.errorMessage.isNullOrEmpty()) {
                    orderCount.postValue(it.data)
                }
            }
        }
    }

    fun requestOderList(page: Int, limit: Int, period: Int, isAdded: Boolean = false) {
        val repo = repository as OrderListRepository
        viewModelScope.launch {
            this@OrderViewModel.page = page
            this@OrderViewModel.period = period
            val result = repo.requestOderList(page, limit, period)
            result?.let {newData->
                if (result.errorMessage == null) {
                    if (isAdded) {
                        val newList = orderList.value?.toMutableList()
                        newList?.addAll(newData.data)
                        orderList.postValue(newList?: mutableListOf())
                    } else {
                        orderList.postValue(newData.data)
                    }
                }
            }
        }
    }

    fun requestUserProfile() {
        viewModelScope.launch {
            val result = signRepo.requestUserProfile()

            result?.let {
                signRepo.userInfoRes = it
            }
        }
    }
}