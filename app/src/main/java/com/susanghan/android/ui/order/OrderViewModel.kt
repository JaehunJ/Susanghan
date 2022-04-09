package com.susanghan.android.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.OrderListRepository
import com.susanghan.android.repository.SignInRepository
import com.susanghan.android.retrofit.response.OrderCountResponse
import com.susanghan.android.retrofit.response.OrderListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Period
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