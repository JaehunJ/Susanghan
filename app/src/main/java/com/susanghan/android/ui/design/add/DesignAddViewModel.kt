package com.susanghan.android.ui.design.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.custom.getImageBodyUri
import com.susanghan.android.repository.DesignRepository
import com.susanghan.android.retrofit.response.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignAddViewModel @Inject constructor(repository: DesignRepository) :
    BaseViewModel(repository) {

    val isSuccess = MutableLiveData<Boolean>()
    val orgData = MutableLiveData<BaseResponse>()

    private val _bluePrintImagePath = MutableLiveData<MutableList<Uri>>()
    val bluePrintImagePath: LiveData<MutableList<Uri>>
        get() = _bluePrintImagePath

    private val _beforeImagePath = MutableLiveData<Uri>()
    val beforeImagePath: LiveData<Uri>
        get() = _beforeImagePath

    private val _afterImagePath = MutableLiveData<Uri>()
    val afterImagePath: LiveData<Uri>
        get() = _afterImagePath

    init {
        _bluePrintImagePath.postValue(mutableListOf())
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
        val blueList = _bluePrintImagePath.value ?: mutableListOf()
        blueList?.let {
            if (it.size + list.size != 5) {
                list.forEach { item ->
                    it.add(item)
                }
            }
        }
        _bluePrintImagePath.postValue(blueList)
    }

    fun deleteBluePrintImage() {

    }

    fun addItem() {

    }

    fun deleteItem() {

    }

    fun addBeforeImage(file: Uri) {
        _beforeImagePath.postValue(file)
    }

    fun deleteBeforeImage() {

    }

    fun addAfterImage(file: Uri) {
        _afterImagePath.postValue(file)
    }

    fun deleteAfterImage() {

    }
}