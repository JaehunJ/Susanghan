package com.oldee.expert.ui.design.add

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.expert.base.BaseViewModel
import com.oldee.expert.custom.getFileName
import com.oldee.expert.custom.getImageBody
import com.oldee.expert.repository.DesignRepository
import com.oldee.expert.retrofit.request.DesignPostRequest
import com.oldee.expert.retrofit.response.BaseResponse
import com.oldee.expert.retrofit.response.DesignDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class DesignAddViewModel @Inject constructor(repository: DesignRepository) :
    BaseViewModel(repository) {
    //    val bluePrintImagePath = MutableLiveData<MutableList<Uri>>()
    val bluePrintImagePostPath = mutableListOf<String>()

    val viewingBluePrintImage = MutableLiveData<MutableList<ImageData>>()
    val uploadBluePrintImage = mutableListOf<ImageData>()

    val beforeImagePath = MutableLiveData<ImageData?>()
    val beforeImagePostPath = mutableListOf<String>()

    val afterImagePath = MutableLiveData<ImageData?>()
    val afterImagePostPath = mutableListOf<String>()

    val oldImage = MutableLiveData<Uri>()

    var price = MutableLiveData<String>()
    var reformName = MutableLiveData<String>()
    var contents = MutableLiveData<String>()

    var minDay = MutableLiveData<String>()
    var maxDay = MutableLiveData<String>()

    var prepareItemList = MutableLiveData<MutableList<PrepareItemRecyclerViewAdapter.PrepareItem>>()

    val postResult = MutableLiveData<BaseResponse?>()

    var reformId = 0
    var mode = MODE_WRITE

    var oldData = MutableLiveData<DesignDetailResponse.DesignDetailData>()

    init {
        viewingBluePrintImage.postValue(mutableListOf())
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

    fun requestOldDesign(id: Int) {
        viewModelScope.launch {
            val result = (repository as DesignRepository).requestDesignDetail(id)

            result?.let {
                if (it.errorMessage.isNullOrEmpty()) {
                    oldData.postValue(it.data)
                }
            }
        }
    }

    private suspend fun requestPostBluePrintImage(context: Context): List<String>? {
        val offlinelist = arrayListOf<File>()

        val resultList = arrayListOf<String>()

        val blueList = viewingBluePrintImage.value

        blueList?.let {
            it.forEach { item ->
                if (item.type == IMAGE_URI && item.uri != null) {
                    val file = copyToScopeStorage(context, item.uri)
                    file?.let {
                        offlinelist.add(file)
                    }
                } else {
                    resultList.add(item.path ?: "")
                }
            }
        }

//        uploadBluePrintImage.forEachIndexed { idx, item ->
//            if (item.type == IMAGE_URI && item.uri != null) {
//                val file = copyToScopeStorage(context, item.uri)
//                file?.let {
//                    offlinelist.add(file)
//                }
//            } else {
//                resultList.add(item.path ?: "")
//            }
//        }

        //오프라인 파일이 존재하면 서버에 업로드
        if (offlinelist.isNotEmpty()) {
            val multiBody = getImageBody("files", offlinelist)
            val repo = repository as DesignRepository
            val res = repo.requestPostImage(multiBody)

            res?.let {
                val imageList = it.data

                if (imageList.isNotEmpty()) {
                    imageList.forEach { d ->
                        resultList.add(d.imageName)
                    }
                }
            }
        }

        return resultList
    }

    private suspend fun requestPostServerOneImage(
        context: Context,
        data: MutableLiveData<ImageData?>
    ): String {
        var str = ""
        val list = arrayListOf<File>()
        data.value?.let {
            if (it.type == IMAGE_URI && it.uri != null) {
                val file = copyToScopeStorage(context, it.uri)
                file?.let {
                    list.add(file)
                }
            } else {
                str = it.path.toString()
            }
        }

        if (list.isNotEmpty()) {
            val multiBody = getImageBody("files", list)
            val repo = repository as DesignRepository
            val res = repo.requestPostImage(multiBody)

            res?.let {
                val imageList = it.data

                if (imageList.isNotEmpty()) {
                    str = imageList[0].imageName
                }
            }
        }

        return str
    }

    private suspend fun requestPostBeforeAfterImage(context: Context): List<String>? {
        val before = requestPostServerOneImage(context, beforeImagePath)
        val after = requestPostServerOneImage(context, afterImagePath)

        return listOf(before, after)

//        val list = arrayListOf<File>()


//        beforeImagePath.value?.let {
//            if (it.uri != null) {
//                val file = copyToScopeStorage(context, it.uri)
//                file?.let {
//                    list.add(file)
//                }
//            }
//        }
//        afterImagePath.value?.let {
//            if (it.uri != null) {
//                val file = copyToScopeStorage(context, it.uri)
//                file?.let {
//                    list.add(file)
//                }
//            }
//        }

//        val multiBody = getImageBody("files", list)
//        val repo = repository as DesignRepository
//        val res = repo.requestPostImage(multiBody)
//
//        res?.let {
//            val imageList = it.data
//
//            if (imageList.isNotEmpty()) {
//                val reList = mutableListOf<String>()
//                imageList.forEach { d ->
//                    reList.add(d.imageName)
//                }
//
//                return reList
//            }
//        }
//
//        return null
    }

    fun addBluePrintImageServer(list: List<String>) {
        val blueList = viewingBluePrintImage.value ?: mutableListOf()

        blueList?.let {
            if (it.size + list.size != 5) {
                list.forEach { item ->
                    val data = ImageData(IMAGE_SERVER, item, null)
                    it.add(data)
                }
            }
        }
        viewingBluePrintImage.postValue(blueList)
    }

    fun addBluePrintImageList(list: List<Uri>) {
        val blueList = viewingBluePrintImage.value ?: mutableListOf()
        blueList?.let {
            if (it.size + list.size != 5) {
                list.forEach { item ->
                    val data = ImageData(IMAGE_URI, null, item)
                    it.add(data)
                    uploadBluePrintImage.add(data)
                }
            }
        }
        viewingBluePrintImage.postValue(blueList)
//        val blueList = viewingBluePrintImage.value ?: mutableListOf()
//        blueList?.let {
//            if (it.size + list.size != 5) {
//                list.forEach { item ->
//                    it.add(item)
//                }
//            }
//        }
//        viewingBluePrintImage.postValue(blueList)
    }

    fun deleteBluePrintImage(index: Int) {
        if (index >= viewingBluePrintImage.value!!.count()) {
            Log.e("#debug", "list is null")
            return
        }
        val list = viewingBluePrintImage.value
        list?.let {
            val item = it[index]
            it.remove(item)
            if (item.type == IMAGE_URI) {
                val temp = uploadBluePrintImage.find { t ->
                    item.uri == t.uri
                }

                temp?.let { t ->
                    uploadBluePrintImage.remove(t)
                }
            }

            viewingBluePrintImage.postValue(it)
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
            minDay.value.isNullOrEmpty()
        }
    }

    fun isValueValidated() =
        !viewingBluePrintImage.value.isNullOrEmpty() && beforeImagePath.value != null && afterImagePath.value != null
                && minDay.value?.isNotEmpty() == true && maxDay.value?.isNotEmpty() == true
                && price.value?.isNotEmpty() == true && reformName.value?.isNotEmpty() == true
                && contents.value?.isNotEmpty() == true &&
                prepareItemList.value?.count()!! > 1


    fun onClickPost(context: Context) {
        viewModelScope.launch {
            //get Image
            val list1 = requestPostBluePrintImage(context)
            val list2 = requestPostBeforeAfterImage(context)

            val bluePrintImageList = mutableListOf<DesignPostRequest.ImageData>()
            list1?.let {
                list1.forEachIndexed { idx, item ->
                    bluePrintImageList.add(
                        DesignPostRequest.ImageData(
                            item,
                            if (idx == 0) 1 else 0
                        )
                    )
                }
            }

            val prepareItems = mutableListOf<DesignPostRequest.ItemData>()
            prepareItemList.value?.forEach {
                if (it.code != "99")
                    prepareItems.add(DesignPostRequest.ItemData(it.name, it.code))
            }

            val request = DesignPostRequest(
                reformName.value ?: "",
                price.value?.toInt() ?: 0,
                contents.value ?: "",
                list2?.get(0) ?: "",
                list2?.get(1) ?: "",
                minDay.value ?: "",
                maxDay.value?.toInt() ?: 0,
                bluePrintImageList,
                prepareItems
            )

            if (mode == MODE_WRITE) {
                val result = (repository as DesignRepository).requestPostDesign(request)
                if (result == null) {

                } else {

                }

                postResult.postValue(result)
            } else {
                val result = (repository as DesignRepository).requestModifyDesign(reformId, request)
                if (result == null) {

                } else {

                }

                postResult.postValue(result)
            }
        }
    }


    fun copyToScopeStorage(context: Context, contentsUri: Uri): File? {
        if(contentsUri.scheme!! == "file"){
            return contentsUri.toFile()
        }

        val parcelFileDiscripor = context.contentResolver?.openFileDescriptor(contentsUri, "r")

        parcelFileDiscripor?.let {
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val inputStream = FileInputStream(it.fileDescriptor)
            val file = File(storageDir, context.contentResolver.getFileName(contentsUri))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            return file
        }

        return null
    }


    fun addBeforeImage(file: Uri) {
        beforeImagePath.postValue(ImageData(IMAGE_URI, "", file))
    }

    fun deleteBeforeImage() {
        beforeImagePath.postValue(null)
    }

    fun addAfterImage(file: Uri) {
        afterImagePath.postValue(ImageData(IMAGE_URI, "", file))
    }

    fun deleteAfterImage() {
        afterImagePath.postValue(null)
    }

    data class ImageData(val type: Int, val path: String?, val uri: Uri?)
}