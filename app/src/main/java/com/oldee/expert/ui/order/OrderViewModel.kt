package com.oldee.expert.ui.order

import android.util.Size
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.custom.ListLiveData
import com.oldee.expert.repository.OrderListRepository
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.response.OrderCountResponse
import com.oldee.expert.retrofit.response.OrderListResponse
import com.oldee.expert.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderCountUseCase: GetOrderCountUseCase,
    private val getOrderListUseCase: GetOrderListUseCase,
    private val getUserProfileUseCase: GetProfileUseCase,
    private val setUserProfileUseCase: SetProfileUseCase,
    private val setImageUseCase: SetImageUseCase
) :
    BaseViewModel() {

    val orderList = ListLiveData<OrderListResponse.OrderData>()

    val orderCount = MutableLiveData<OrderCountResponse.OrderCountData>()

    var page = 0
    var period = 0


    fun requestOrderCount() {
        remote(false) {
            val result = getOrderCountUseCase()

            result?.let {
                if (result.errorMessage.isNullOrEmpty()) {
                    orderCount.postValue(it.data)
                }
            }
        }
        viewModelScope.launch(connectionExceptionHandler) {

        }
    }

    fun requestOderList(page: Int, limit: Int, period: Int, isAdded: Boolean = false) {
        remote {
            this@OrderViewModel.page = page
            this@OrderViewModel.period = period
            val result = getOrderListUseCase(page, limit, period)
            result?.let { newData ->
                if (result.errorMessage == null) {
                    if (isAdded) {
                        if (newData.data.isNotEmpty())
                            orderList.add(newData.data)
                    } else {
                        orderList.replace(newData.data.toMutableList())
                    }
                }
            }
        }
    }

    fun requestUserProfile() {
        viewModelScope.launch(connectionExceptionHandler) {
            val result = getUserProfileUseCase()

            result?.let {
                setUserProfileUseCase(it)
            }
        }
    }

    fun setImage(imageView: ImageView, url: String, size: Size? = null){
        remote(false) {
            setImageUseCase(imageView, url, size)
        }
    }

    fun setImageRound(imageView: ImageView, url:String, roundDp:Int){
        remote(false) {
            setImageUseCase(imageView, url, roundDp)
        }
    }
}