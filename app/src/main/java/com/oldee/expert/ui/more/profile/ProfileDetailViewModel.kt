package com.oldee.expert.ui.more.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.response.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(repository: SignInRepository) :
    BaseViewModel(repository) {

    val data = MutableLiveData<ProfileResponse?>()

    fun requestUserProfile() {
        viewModelScope.launch {
            val result = (repository as SignInRepository).requestUserProfile()

            data.postValue(result)
        }
    }
}