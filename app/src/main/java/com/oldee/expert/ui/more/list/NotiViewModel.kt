package com.oldee.expert.ui.more.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.EtcServiceRepository
import com.oldee.expert.retrofit.response.NoticeData
import com.oldee.expert.usecase.GetNoticeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor(private val getNoticeUseCase: GetNoticeUseCase) :
    BaseViewModel() {

    val notiData = MutableLiveData<MutableList<NoticeData>>()

    fun requestNotice(page: Int) {
        remote {
            val result = getNoticeUseCase(page)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    notiData.postValue(it.data)
                }
            }
        }
    }
}