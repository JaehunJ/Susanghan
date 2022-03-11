package com.susanghan.android.ui.signup

import androidx.lifecycle.MutableLiveData
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpPwViewModel @Inject constructor(repository: BaseRepository) :
    BaseViewModel(repository) {

    val pw = MutableLiveData<String>()
    val pwConfirm = MutableLiveData<String>()

    val isCb0 = MutableLiveData<Boolean>()
    val isCb1 = MutableLiveData<Boolean>()
    val isCb2 = MutableLiveData<Boolean>()
}