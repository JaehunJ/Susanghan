package com.oldee.expert.ui.more.faq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.EtcServiceRepository
import com.oldee.expert.retrofit.response.FaqData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(repository: EtcServiceRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<List<FaqData>>()

    fun requestFaqList(page: Int, limit: Int) {
        val repo = repository as EtcServiceRepository
        viewModelScope.launch(connectionExceptionHandler) {
            val result = repo.requestFaq(page, limit)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    data.postValue(it.data)
                }
            }
        }
    }
}