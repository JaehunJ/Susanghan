package com.susanghan.android.ui.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.SignInRepository
import com.susanghan.android.retrofit.response.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(repository: SignInRepository):BaseViewModel(repository) {
    val profile = MutableLiveData<ProfileResponse.ProfileData>()

    fun requestProfile(){
        viewModelScope.launch {
            val result = (repository as SignInRepository).requestUserProfile()

            result?.let{
                if(it.errorMessage.isNullOrEmpty()){
                    profile.postValue(it.data)
                }
            }
        }
    }
}