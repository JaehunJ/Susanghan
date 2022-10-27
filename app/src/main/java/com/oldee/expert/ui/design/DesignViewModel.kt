package com.oldee.expert.ui.design

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.data.ReformStatus
import com.oldee.expert.repository.DesignRepository
import com.oldee.expert.retrofit.response.DesignListResponse
import com.oldee.expert.usecase.GetDesignListUseCase
import com.oldee.expert.usecase.SetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignViewModel @Inject constructor(
    private val getDesignListUseCase: GetDesignListUseCase,
    private val setImageUseCase: SetImageUseCase
    ) :
    BaseViewModel() {

    val designList = MutableLiveData<List<DesignListResponse.DesignData>?>()

    var page = 0
    var reformStatus = MutableLiveData<ReformStatus>()
//    var reformStatusEnum = ReformStatus.None

    init {
        reformStatus.postValue(ReformStatus.None)
    }

    fun setImage(imageView: ImageView, url:String){
        remote {
            setImageUseCase(imageView, url)
        }
    }

    fun requestDesignList(page: Int, limit: Int, status: ReformStatus, isAdded: Boolean = false) {
        remote {
            this@DesignViewModel.page = page
//            val result = repo.requestDesignList(page, limit, status)
            val result = getDesignListUseCase(page, limit, status.value)

            if (result != null) {
                if (result.errorMessage == null) {
                    if (isAdded) {
                        val newList = mutableListOf<DesignListResponse.DesignData>()
                        val oldList = designList.value

                        oldList?.let { l ->
                            l.forEach { item ->
                                newList.add(item)
                            }
                        }

                        result.data.forEach {
                            newList.add(it)
                        }

                        designList.postValue(newList.toList())

                    } else {
                        designList.postValue(result.data)
                    }
                } else {
                    designList.postValue(null)
                }
            } else {
                designList.postValue(null)
            }
        }
    }
}