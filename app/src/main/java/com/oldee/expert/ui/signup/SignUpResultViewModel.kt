package com.oldee.expert.ui.signup

import com.oldee.expert.base.BaseRepository
import com.oldee.expert.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpResultViewModel @Inject constructor(repository: BaseRepository) :
    BaseViewModel(repository) {
}