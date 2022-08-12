package com.oldee.expert.ui.order.detail

import android.util.Size
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.OrderListRepository
import com.oldee.expert.retrofit.request.OrderStatusUpdateRequest
import com.oldee.expert.retrofit.response.DeliveryListResponse
import com.oldee.expert.retrofit.response.OrderDetailResponse
import com.oldee.expert.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getOrderDetailUseCase: GetOrderDetailUseCase,
    private val postOrderStatusChangeUseCase: PostOrderStatusChangeUseCase,
    private val getDeliveryListUseCase: GetDeliveryListUseCase,
    private val setImageUseCase: SetImageUseCase
) :
    BaseViewModel() {

    val data = MutableLiveData<OrderDetailResponse>()
    val deliveryList = MutableLiveData<DeliveryListResponse>()
    var courierCode: String = ""
    var invoiceNumber: String = ""
    val successStatusChange = MutableLiveData<Boolean>()

    fun requestOrderDetail(id: Int) {
        remote {
            val result = getOrderDetailUseCase(id)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    data.postValue(it)
                }
            }
        }
    }

    fun requestChangeStatus(status: Int) {
        remote {
            val arr = mutableListOf<Int>()
            data.value?.let {
                it.data.forEach { d ->
                    arr.add(d.orderDetailId ?: 0)
                }
            }

            val result = postOrderStatusChangeUseCase(
                OrderStatusUpdateRequest(arr, status, courierCode, invoiceNumber)
            )

            result?.let {
                successStatusChange.postValue(it.errorMessage.isNullOrEmpty())
            }
        }
    }

    fun requestCarriedCompany() {
        remote {
            val result = getDeliveryListUseCase()

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    deliveryList.postValue(it)
                }
            }
        }
    }

    fun setImage(iv: ImageView, url:String, size: Size? = null){
        remote {
            setImageUseCase(iv, url, size)
        }
    }
}