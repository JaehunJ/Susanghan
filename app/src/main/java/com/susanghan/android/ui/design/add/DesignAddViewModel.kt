package com.susanghan.android.ui.design.add

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.custom.getFileName
import com.susanghan.android.custom.getImageBody
import com.susanghan.android.custom.getImageBodyUri
import com.susanghan.android.repository.DesignRepository
import com.susanghan.android.retrofit.request.DesignPostRequest
import com.susanghan.android.retrofit.response.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DesignAddViewModel @Inject constructor(repository: DesignRepository) :
    BaseViewModel(repository) {

    val bluePrintImagePath = MutableLiveData<MutableList<Uri>>()
    val bluePrintImagePostPath = mutableListOf<String>()

    val beforeImagePath = MutableLiveData<Uri>()
    val beforeImagePostPath = mutableListOf<String>()

    val afterImagePath = MutableLiveData<Uri>()
    val afterImagePostPath = mutableListOf<String>()

    var price: String = ""
    var reformName: String = ""
    var contents: String = ""

    var minDay: String = ""
    var maxDay: String = ""

    var prepareItemList = MutableLiveData<MutableList<PrepareItemRecyclerViewAdapter.PrepareItem>>()

    val postResult = MutableLiveData<BaseResponse?>()

    init {
        bluePrintImagePath.postValue(mutableListOf())
        prepareItemList.postValue(
            mutableListOf(
                PrepareItemRecyclerViewAdapter.PrepareItem(
                    "99",
                    ""
                )
            )
        )
    }

    fun requestPostDesign() {
        viewModelScope.launch {

        }
    }

    fun requestModifyDesign() {
        viewModelScope.launch {

        }
    }

    private suspend fun requestPostBluePrintImage(context:Context): List<String>? {
        val list = arrayListOf<File>()

        bluePrintImagePath.value?.forEach {
            val file = copyToScopeStorage(context, it)
            file?.let{
                list.add(file)
            }
        }

//        val multiBody = getImageBodyUri("files", list)
        val multiBody = getImageBody("files", list)
        val repo = repository as DesignRepository
        val res = repo.requestPostImage(multiBody)

        res?.let {
            val imageList = it.data

            if (imageList.isNotEmpty()) {
                val reList = mutableListOf<String>()
                imageList.forEach { d ->
                    reList.add(d.imageName)
                }
                return reList
            }
        }

        return null
    }

    private suspend fun requestPostBeforeAfterImage(context: Context): List<String>? {
        val list = arrayListOf<File>()

        beforeImagePath.value?.let {
            val file = copyToScopeStorage(context, it)
            file?.let{
                list.add(file)
            }
        }
        afterImagePath.value?.let {
            val file = copyToScopeStorage(context, it)
            file?.let{
                list.add(file)
            }
        }

        val multiBody = getImageBody("files", list)
        val repo = repository as DesignRepository
        val res = repo.requestPostImage(multiBody)

        res?.let {
            val imageList = it.data

            if (imageList.isNotEmpty()) {
                val reList = mutableListOf<String>()
                imageList.forEach { d ->
                    reList.add(d.imageName)
                }

                return reList
            }
        }

        return null
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

    fun deleteBluePrintImage(index: Int) {
        if (index >= bluePrintImagePath.value!!.count()) {
            Log.e("#debug", "list is null")
        }
        val list = bluePrintImagePath.value
        list?.let {
            it.removeAt(index)
            bluePrintImagePath.postValue(it)
        }
    }

    fun addPrepareItem(item: PrepareItemRecyclerViewAdapter.PrepareItem) {
        val end = PrepareItemRecyclerViewAdapter.PrepareItem("99", "")
        val add = item

        val list = prepareItemList.value

        list?.let {
            val lastItem = it[list.lastIndex]
            it.remove(lastItem)
            it.add(add)
            it.add(end)

            prepareItemList.postValue(it)
        }
    }

    fun onClickPost(context: Context) {
        viewModelScope.launch {
            //get Image
            val list1 = requestPostBluePrintImage(context)
//            val list2 = requestPostBeforeAfterImage(context)

//            val bluePrintImageList = mutableListOf<DesignPostRequest.ImageData>()
//            list1?.let{
//                list1.forEach { item->
//                    bluePrintImageList.add(DesignPostRequest.ImageData(item, 0))
//                }
//            }
//
//            val prepareItems = mutableListOf<DesignPostRequest.ItemData>()
//            prepareItemList.value?.forEach {
//                prepareItems.add(DesignPostRequest.ItemData(it.name, it.code))
//            }
//
//            val request = DesignPostRequest(reformName, price.toInt(), contents, list2?.get(0)?:"",
//            list2?.get(1)?:"", minDay, maxDay.toInt(), bluePrintImageList, prepareItems)
//
//            val result = (repository as DesignRepository).requestPostDesign(request)
//            postResult.postValue(result)
        }
    }


    fun copyToScopeStorage(context:Context, contentsUri: Uri):File?{
        val parcelFileDiscripor = context.contentResolver?.openFileDescriptor(contentsUri, "r")

        parcelFileDiscripor?.let{
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            var tempfilePath = File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            ).absolutePath
            val inputStream = FileInputStream(it.fileDescriptor)
            val file = File(storageDir, context.contentResolver.getFileName(contentsUri))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            return file
        }

        return null
    }


    fun addBeforeImage(file: Uri) {
        beforeImagePath.postValue(file)
    }

    fun addAfterImage(file: Uri) {
        afterImagePath.postValue(file)
    }
}