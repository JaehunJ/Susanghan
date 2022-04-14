package com.oldee.expert.ui.more.notice

import com.oldee.expert.base.BaseRepository
import com.oldee.expert.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserNotificationViewModel @Inject constructor(repository: BaseRepository) :
    BaseViewModel(repository) {
}