package com.susanghan.android.ui.more.adjustment

import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdjustmentViewModel @Inject constructor(repository: BaseRepository) :
    BaseViewModel(repository) {
}