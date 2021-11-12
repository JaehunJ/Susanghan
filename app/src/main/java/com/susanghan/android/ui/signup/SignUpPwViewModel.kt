package com.susanghan.android.ui.signup

import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpPwViewModel @Inject constructor(repository: BaseRepository) :
    BaseViewModel(repository) {
}