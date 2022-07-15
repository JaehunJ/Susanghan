package com.oldee.expert.ui.error

import com.oldee.expert.base.BaseRepository
import com.oldee.expert.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkErrorViewModel @Inject constructor(repository: BaseRepository):BaseViewModel(repository){

}