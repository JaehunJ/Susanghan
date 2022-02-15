package com.susanghan.android.ui.design.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.DesignRepository
import com.susanghan.android.retrofit.response.DesignDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignDetailViewModel @Inject constructor(repository: DesignRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<DesignDetailResponse.DesignDetailData>()
    val imageList = MutableLiveData<List<DesignImageAdapter.DesignDetailBigImage>>()

    fun requestDesignDetail(id: Int) {
        viewModelScope.launch {

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
}