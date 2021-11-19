package com.susanghan.android.ui.repairer

import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepairerViewModel @Inject constructor(repository: BaseRepository):BaseViewModel(repository) {
}