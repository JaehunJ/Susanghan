package com.susanghan.android.ui.design

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.repository.DesignRepository
import com.susanghan.android.retrofit.response.DesignListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignViewModel @Inject constructor(repository: DesignRepository) :
    BaseViewModel(repository) {

    val designList = MutableLiveData<List<DesignListResponse.DesignData>?>()

    fun requestDesignList(page: Int, limit: Int, status: Int) {
        val repo = super.repository as DesignRepository
        viewModelScope.launch {
            val result = repo.requestDesignList(page, limit, status)

            if (result != null) {
                if (result.errorMessage == null) {
                    designList.postValue(result.data)
                } else {
                    designList.postValue(null)
                }
            } else {
                designList.postValue(null)
            }
        }

//        Log.e("rx", "add data")
//        //temp
//        val tempList = listOf<DesignListAdapter.TempDesignItem>(
//            DesignListAdapter.TempDesignItem("", "Sony", 5, 0),
//            DesignListAdapter.TempDesignItem("", "Sony1", 5, 1),
//            DesignListAdapter.TempDesignItem("", "Sony2", 5, 2),
//            DesignListAdapter.TempDesignItem("", "Sony3", 5, 3),
//            DesignListAdapter.TempDesignItem("", "Sony4", 5, 4),
//            DesignListAdapter.TempDesignItem("", "Sony5", 5, 5),
//            DesignListAdapter.TempDesignItem("", "Sony6", 5, 6),
//            DesignListAdapter.TempDesignItem("", "Sony7", 5, 7),
//            DesignListAdapter.TempDesignItem("", "Sony8", 5, 8)
//        )
//
//        designList.postValue(tempList)
    }
}