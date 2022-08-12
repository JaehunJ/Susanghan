package com.oldee.expert.ui.more.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.response.ProfileResponse
import com.oldee.expert.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(private val getUserProfileUseCase: GetProfileUseCase) :
    BaseViewModel() {

    val data = MutableLiveData<ProfileResponse?>()

    fun requestUserProfile() {
        remote {
            val result = getUserProfileUseCase()

            data.postValue(result)
        }
    }
}