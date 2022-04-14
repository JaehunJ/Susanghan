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

    val orderList = MutableLiveData<List<OrderListResponse.OrderData>>()
    val orderCount = MutableLiveData<OrderCountResponse.OrderCountData>()

    var page = 0
    var period = 0

    fun requestOrderCount(){
        val repo = repository as OrderListRepository
        viewModelScope.launch {
            val result = repo.requestOrderCount()

            result?.let{
                if(result.errorMessage.isNullOrEmpty()){
                    orderCount.postValue(it.data)
                }
            }
        }
    }

    fun requestOderList(page: Int, limit: Int, period: Int) {
        val repo = repository as OrderListRepository
        viewModelScope.launch {
            this@OrderViewModel.page =page
            this@OrderViewModel.period = period
            val result = repo.requestOderList(page, limit, period)
            result?.let {
                if (result.errorMessage == null)
                    orderList.postValue(it.data)
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

    fun requestDeliveryList(){
        viewModelScope
    }
}