package com.susanghan.android.ui.order.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.OrderListRepository
import com.susanghan.android.retrofit.response.OrderDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(repository: OrderListRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<OrderDetailResponse>()

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
}