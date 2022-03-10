package com.susanghan.android.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.OrderListRepository
import com.susanghan.android.repository.SignInRepository
import com.susanghan.android.retrofit.response.OrderListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(repository: OrderListRepository, val signRepo:SignInRepository) :
    BaseViewModel(repository) {

    val orderList = MutableLiveData<List<OrderListResponse.OrderData>>()

    fun requestOderList(page: Int, limit: Int, period: Int) {
        val repo = repository as OrderListRepository
        viewModelScope.launch {
            val result = repo.requestOderList(page, limit, period)
            result?.let {
                if (result.errorMessage == null)
                    orderList.postValue(it.data)
            }
        }
    }

    fun requestUserProfile(){
        viewModelScope.launch {
            val result = signRepo.requestUserProfile()

            result?.let{
                signRepo.userInfoRes = it
            }
        }
    }
}