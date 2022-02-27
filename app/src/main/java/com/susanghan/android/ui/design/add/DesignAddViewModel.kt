package com.susanghan.android.ui.design.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.custom.getImageBodyUri
import com.susanghan.android.repository.DesignRepository
import com.susanghan.android.retrofit.request.DesignPostRequest
import com.susanghan.android.retrofit.response.BaseResponse
import com.susanghan.android.ui.dialog.PrepareItemDialogFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignAddViewModel @Inject constructor(repository: DesignRepository) :
    BaseViewModel(repository) {

    val bluePrintImagePath = MutableLiveData<MutableList<Uri>>()
    val beforeImagePath = MutableLiveData<Uri>()
    val afterImagePath = MutableLiveData<Uri>()

    var price:String = ""
    var reformName:String = ""
    var contents:String = ""

    var minDay:String = ""
    var maxDay:String = ""

    var prepareItemList = MutableLiveData<MutableList<PrepareItemRecyclerViewAdapter.PrepareItem>>()

    init {
        bluePrintImagePath.postValue(mutableListOf())
        prepareItemList.postValue(mutableListOf(PrepareItemRecyclerViewAdapter.PrepareItem("99","")))
    }

    fun requestPostDesign() {
        viewModelScope.launch {

        }
    }

    fun requestModifyDesign() {
        viewModelScope.launch {

        }
    }

    fun requestPostImage() {
        viewModelScope.launch {
            val list = arrayListOf<Uri>()

            bluePrintImagePath.value?.forEach {
                list.add(it)
            }
            beforeImagePath.value?.let {
                list.add(it)
            }
            afterImagePath.value?.let {
                list.add(it)
            }

            val multiBody = getImageBodyUri("files", list)
            val repo = repository as DesignRepository
            repo.requestPostImage(multiBody)
        }
    }

    fun addBluePrintImage(list: List<Uri>) {
        val blueList = bluePrintImagePath.value ?: mutableListOf()
        blueList?.let {
            if (it.size + list.size != 5) {
                list.forEach { item ->
                    it.add(item)
                }
            }
        }
        bluePrintImagePath.postValue(blueList)
    }

    fun deleteBluePrintImage(index:Int) {
        if(index >= bluePrintImagePath.value!!.count()){
            Log.e("#debug", "list is null")
        }
        val list = bluePrintImagePath.value
        list?.let{
            it.removeAt(index)
            bluePrintImagePath.postValue(it)
        }
    }

    fun addPrepareItem(item:PrepareItemRecyclerViewAdapter.PrepareItem) {
        val end = PrepareItemRecyclerViewAdapter.PrepareItem("99","")
        val add = item

        val list = prepareItemList.value

        list?.let{
            val lastItem = it[list.lastIndex]
            it.remove(lastItem)
            it.add(item)
            it.add(end)

            prepareItemList.postValue(it)
        }
    }

    fun deletePrepareItem() {

    }

    fun addBeforeImage(file: Uri) {
        beforeImagePath.postValue(file)
    }

    fun addAfterImage(file: Uri) {
        afterImagePath.postValue(file)
    }
}