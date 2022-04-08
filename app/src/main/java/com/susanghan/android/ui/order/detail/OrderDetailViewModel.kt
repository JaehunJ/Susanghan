package com.susanghan.android.ui.order.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.OrderListRepository
import com.susanghan.android.retrofit.request.OrderStatusUpdateRequest
import com.susanghan.android.retrofit.response.DeliveryListResponse
import com.susanghan.android.retrofit.response.OrderDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(repository: OrderListRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<OrderDetailResponse>()
    val deliveryList = MutableLiveData<DeliveryListResponse>()
    var courierCode:String = ""
    var invoiceNumber:String = ""
    val successStatusChange = MutableLiveData<Boolean>()

    fun requestOrderDetail(id: Int) {
        viewModelScope.launch {
            val result = (repository as OrderListRepository).requestOrderDetail(id)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    data.postValue(it)
                }
            }
        }
    }

    fun requestChangeStatus(status:Int){
        viewModelScope.launch {
            val arr = mutableListOf<Int>()
            data.value?.let{
                it.data.forEach { d->
                    arr.add(d.orderDetailId?:0)
                }
            }

            val result = (repository as OrderListRepository).requestChangeOrderStatus(
                OrderStatusUpdateRequest(arr, status, courierCode, invoiceNumber)
            )

            result?.let{
                successStatusChange.postValue(it.errorMessage.isNullOrEmpty())
//                if(it.errorMessage.isNullOrEmpty()){
//
//                }
            }
        }
    }

    fun requestCarriedCompany(){
        viewModelScope.launch {
            val result = (repository as OrderListRepository).requestDeliveryList()

            result?.let{
                if(it.errorMessage.isNullOrEmpty()){
                    deliveryList.postValue(it)
                }
            }
        }
    }
}