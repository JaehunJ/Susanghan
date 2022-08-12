package com.oldee.expert.ui.more

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.repository.SignInRepository
import com.oldee.expert.retrofit.response.ProfileResponse
import com.oldee.expert.usecase.GetProfileUseCase
import com.oldee.expert.usecase.SetImageCircleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(private val getUserProfileUseCase: GetProfileUseCase,
private val setImageCircleUseCase: SetImageCircleUseCase) : BaseViewModel() {
    val profile = MutableLiveData<ProfileResponse.ProfileData?>()
    val versionName = MutableLiveData<String>()
//
//    init {
//        versionName.postValue()
//    }

    fun requestProfile() {
        remote {
            val result = getUserProfileUseCase()

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    profile.postValue(it.data)
                }
            }
        }
    }

    fun getVersionInfo(context: Context) {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        versionName.postValue(info.versionName)
    }

    fun setImageCircle(imageView:ImageView, url:String){
        remote {
            setImageCircleUseCase(imageView, url)
        }
    }
}