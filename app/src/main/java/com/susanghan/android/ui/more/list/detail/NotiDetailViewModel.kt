package com.susanghan.android.ui.more.list.detail

import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotiDetailViewModel @Inject constructor(repository: BaseRepository) :
    BaseViewModel(repository) {
}