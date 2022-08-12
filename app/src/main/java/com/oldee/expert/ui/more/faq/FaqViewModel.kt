package com.oldee.expert.ui.more.faq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.EtcServiceRepository
import com.oldee.expert.retrofit.response.FaqData
import com.oldee.expert.usecase.GetFaqUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(
    private val getFaqUseCase: GetFaqUseCase
) :
    BaseViewModel() {

    val data = MutableLiveData<List<FaqData>>()

    fun requestFaqList(page: Int, limit: Int) {
        remote {
            val result = getFaqUseCase(page, limit)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    data.postValue(it.data)
                }
            }
        }
    }
}