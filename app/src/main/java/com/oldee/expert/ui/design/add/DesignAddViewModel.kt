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
import com.oldee.expert.data.MAX_PRICE
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

    val BLUE_IMAGE_MAX = 5

    val viewingBluePrintImage = MutableLiveData<MutableList<ImageData>>()
    fun getBluePrintImageCount() = viewingBluePrintImage.value?.count() ?: 0

    val beforeImagePath = MutableLiveData<ImageData?>()

    val afterImagePath = MutableLiveData<ImageData?>()

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

    /**
     * 미리보기 이미지 처리, local image가 있다면 서버에 업로드후 path를 받아와서 사용
     *
     * @param context
     * @return
     */
    private suspend fun requestPostBluePrintImage(
        context: Context
    ): List<String>? {
        val offlinelist = arrayListOf<File>()

        val resultList = arrayListOf<String>()

        val blueList = viewingBluePrintImage.value

        blueList?.let {
            it.forEach { item ->
                //현재 등록된 이미지중에 로컬 이미지가 있다면
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

        //오프라인 파일이 존재하면 서버에 업로드
        if (offlinelist.isNotEmpty()) {
            val multiBody = getImageBody("files", offlinelist)
            val repo = repository as DesignRepository
            val res = repo.requestPostImage(null, multiBody) ?: return null

            res.let {
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
            val res = repo.requestPostImage(null, multiBody)

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

        if (before.isNotEmpty() || after.isNotEmpty())
            return listOf(before, after)

        return null
    }

    fun addBluePrintImageServer(list: List<String>) {
        val blueList = viewingBluePrintImage.value ?: mutableListOf()

        blueList?.let {
            if (it.size < BLUE_IMAGE_MAX) {
                list.forEach { item ->
                    val data = ImageData(IMAGE_SERVER, item, null)
                    it.add(data)
                }
            }
        }
        viewingBluePrintImage.postValue(blueList)
    }

    fun addBluePrintImageList(uri: Uri, onError: () -> Unit) {
        val blueList = viewingBluePrintImage.value ?: mutableListOf()
        blueList?.let {
            if (it.size < BLUE_IMAGE_MAX) {
                val match = it.find { old->old.uri == uri }

                if(match == null){
                    val data = ImageData(IMAGE_URI, null, uri)
                    it.add(data)
                }else{
                    onError()
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

            viewingBluePrintImage.postValue(it.toMutableList())
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


    /**
     * 서버에 전송
     *
     * @param context
     * @param onError
     */
    fun onClickPost(context: Context, onError: () -> Unit) {
        viewModelScope.launch {
            //get Image
            //미리보기 이미지 리스트
            val list1 = requestPostBluePrintImage(context)
            //수선 전,후 이미지 리스트
            val list2 = requestPostBeforeAfterImage(context)

            if (list1 == null || list2 == null)
                onError()

            //미리보기 이미지 처리
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

            //준비물 처리
            val prepareItems = mutableListOf<DesignPostRequest.ItemData>()
            prepareItemList.value?.forEach {
                if (it.code != "99")
                    prepareItems.add(DesignPostRequest.ItemData(it.name, it.code))
            }

            //post data
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

            //신규 등록이면 post
            if (mode == MODE_WRITE) {
                val result = (repository as DesignRepository).requestPostDesign(request)
                if (result == null) {

                } else {

                }

                postResult.postValue(result)
            } else { //수정이라면 put
                val result = (repository as DesignRepository).requestModifyDesign(reformId, request)
                if (result == null) {

                } else {

                }

                postResult.postValue(result)
            }
        }
    }


    /**
     * 파일을 앱 저장소로 복사
     *
     * @param context
     * @param contentsUri
     * @return
     */
    fun copyToScopeStorage(context: Context, contentsUri: Uri): File? {
        if (contentsUri.scheme!! == "file") {
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

    /**
     * 이전 이미지 추가
     *
     * @param file
     */
    fun addBeforeImage(file: Uri) {
        beforeImagePath.postValue(ImageData(IMAGE_URI, "", file))
    }

    /**
     * 이전 이미지 삭제
     *
     */
    fun deleteBeforeImage() {
        beforeImagePath.postValue(null)
    }

    /**
     * 수선전 이미지 추가
     *
     * @param file
     */
    fun addAfterImage(file: Uri) {
        afterImagePath.postValue(ImageData(IMAGE_URI, "", file))
    }

    /**
     * 수선후 이미지 삭제
     *
     */
    fun deleteAfterImage() {
        afterImagePath.postValue(null)
    }

    /**
     * viewModel에서 쓸 imageData, remote이미지와 local이미지 통합 관리를 위해 생성
     *
     * @property type
     * @property path
     * @property uri
     */
    data class ImageData(val type: Int, val path: String?, val uri: Uri?)
}