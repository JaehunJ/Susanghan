package com.susanghan.android.ui.more.profile

import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(repository: SignInRepository) :
    BaseViewModel(repository) {

    fun requestUserProfile() {
        viewModelScope.launch {
            val result = (repository as SignInRepository).requestUserProfile()

        }
    }
}