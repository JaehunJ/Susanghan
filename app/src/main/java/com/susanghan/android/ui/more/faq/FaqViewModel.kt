package com.susanghan.android.ui.more.faq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.EtcServiceRepository
import com.susanghan.android.retrofit.response.FaqData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(repository: EtcServiceRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<List<FaqData>>()

    fun requestFaqList(page: Int, limit: Int) {
        val repo = repository as EtcServiceRepository
        viewModelScope.launch {
            val result = repo.requestFaq(page, limit)

            result?.let{
                if(it.errorMessage.isNullOrEmpty()){
                    data.postValue(it.data)
                }
            }
        }
    }
}