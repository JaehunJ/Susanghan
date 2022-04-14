package com.oldee.expert.ui.more.adjustment

import com.oldee.expert.base.BaseRepository
import com.oldee.expert.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdjustmentViewModel @Inject constructor(repository: BaseRepository) :
    BaseViewModel(repository) {
}