package com.oldee.expert.ui.design.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.DesignRepository
import com.oldee.expert.retrofit.request.DesignStatusUpdateRequest
import com.oldee.expert.retrofit.response.DesignDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignDetailViewModel @Inject constructor(repository: DesignRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<DesignDetailResponse.DesignDetailData>()
    val imageList = MutableLiveData<List<DesignImageAdapter.DesignDetailBigImage>>()

    fun requestDesignDetail(id: Int) {
        viewModelScope.launch(connectionExceptionHandler) {
            val repo = super.repository as DesignRepository
            val result = repo.requestDesignDetail(id)

            if (result != null) {
                data.postValue(result.data)
                val images = mutableListOf<DesignImageAdapter.DesignDetailBigImage>()

                result.data.images.forEach {
                    images.add(DesignImageAdapter.DesignDetailBigImage(it))
                }

                imageList.postValue(images)
            }
        }
    }

    fun requestDesignDetailStateUpdate(reformId: Int, status: Int, onError: (() -> Unit)? = null) {
        viewModelScope.launch(connectionExceptionHandler) {
            val repo = repository as DesignRepository
            val result = repo.requestChangeDesignStatus(reformId, DesignStatusUpdateRequest(status))

            if (result != null) {
                var data = result.data as String
                if (data.isNotEmpty() && data == "No Delete") {
                    onError?.invoke()
                } else {
                    requestDesignDetail(reformId)
                }
            }
        }
    }
}