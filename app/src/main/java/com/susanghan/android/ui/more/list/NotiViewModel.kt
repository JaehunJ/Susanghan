package com.susanghan.android.ui.more.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.EtcServiceRepository
import com.susanghan.android.retrofit.response.NoticeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor(repository: EtcServiceRepository) :
    BaseViewModel(repository) {

    val notiData = MutableLiveData<MutableList<NoticeResponse.NoticeData>>()

    fun requestNotice(page: Int) {
        viewModelScope.launch {
            val result = (repository as EtcServiceRepository).requestNotice(page)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    notiData.postValue(it.data)
                }
            }
        }
    }
}