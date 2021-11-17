package com.susanghan.android.ui.design

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DesignViewModel @Inject constructor(repository: BaseRepository) :BaseViewModel(repository){

    val designList = MutableLiveData<List<DesignListAdapter.TempDesignItem>>()

    fun requestDesignList(){
        Log.e("rx", "add data")
        //temp
        val tempList = listOf<DesignListAdapter.TempDesignItem>(
            DesignListAdapter.TempDesignItem("", "Sony", 5, 0),
            DesignListAdapter.TempDesignItem("", "Sony1", 5, 1),
            DesignListAdapter.TempDesignItem("", "Sony2", 5, 2),
            DesignListAdapter.TempDesignItem("", "Sony3", 5, 3),
            DesignListAdapter.TempDesignItem("", "Sony4", 5, 4),
            DesignListAdapter.TempDesignItem("", "Sony5", 5, 5),
            DesignListAdapter.TempDesignItem("", "Sony6", 5, 6),
            DesignListAdapter.TempDesignItem("", "Sony7", 5, 7),
            DesignListAdapter.TempDesignItem("", "Sony8", 5, 8)
        )

        designList.postValue(tempList)
    }
}