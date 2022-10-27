package com.oldee.expert.ui.design.detail

import android.util.Size
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.retrofit.request.DesignStatusUpdateRequest
import com.oldee.expert.retrofit.response.DesignDetailResponse
import com.oldee.expert.usecase.GetDesignDetailUseCase
import com.oldee.expert.usecase.PostDesignStatusChangeUseCase
import com.oldee.expert.usecase.SetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DesignDetailViewModel @Inject constructor(
    private val getDesignDetailUseCase: GetDesignDetailUseCase,
    private val postDesignStatusChangeUseCase: PostDesignStatusChangeUseCase,
    private val setImageUseCase: SetImageUseCase
) :
    BaseViewModel() {

    val data = MutableLiveData<DesignDetailResponse.DesignDetailData>()
    val imageList = MutableLiveData<List<DesignImageAdapter.DesignDetailBigImage>>()

    fun setImage(imageView: ImageView, url: String, size: Size? = null) {
        remote(false) {
            setImageUseCase(imageView, url, size)
        }
    }

    fun setImage(imageView: ImageView, url: String, dp:Int, size: Size? = null){
        remote(false) {
            setImageUseCase(imageView, url, dp, size)
        }
    }

    fun requestDesignDetail(id: Int) {
        remote {
            val result = getDesignDetailUseCase(id)

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
        remote {
            val result = postDesignStatusChangeUseCase(reformId, DesignStatusUpdateRequest(status))

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