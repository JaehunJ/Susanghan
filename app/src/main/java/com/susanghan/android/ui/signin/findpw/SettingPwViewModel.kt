package com.susanghan.android.ui.signin.findpw

import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingPwViewModel @Inject constructor(repository: BaseRepository) :
    BaseViewModel(repository) {
}