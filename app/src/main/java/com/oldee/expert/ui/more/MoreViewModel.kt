package com.oldee.expert.ui.more

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.retrofit.response.ProfileResponse
import com.oldee.expert.retrofit.response.VersionData
import com.oldee.expert.usecase.GetProfileUseCase
import com.oldee.expert.usecase.GetVersionInfoUseCase
import com.oldee.expert.usecase.SetImageCircleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getUserProfileUseCase: GetProfileUseCase,
    private val setImageCircleUseCase: SetImageCircleUseCase,
    private val getVersionInfoUseCase: GetVersionInfoUseCase
) : BaseViewModel() {
    val profile = MutableLiveData<ProfileResponse.ProfileData?>()
    val versionName = MutableLiveData<String>()

    val versionInfo = MutableLiveData<VersionData>()
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

    fun requestRemoteVersionInfo(){
        remote {
            val result = getVersionInfoUseCase()

            result?.let{
                versionInfo.postValue(it.data)
            }
        }
    }

    fun getVersionInfo(context: Context) {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        versionName.postValue(info.versionName)
    }

    fun setImageCircle(imageView: ImageView, url: String) {
        remote {
            setImageCircleUseCase(imageView, url)
        }
    }
}