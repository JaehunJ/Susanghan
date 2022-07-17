package com.oldee.expert.ui.design.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.oldee.expert.BuildConfig
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.data.MAX_PRICE
import com.oldee.expert.data.MIN_PRICE
import com.oldee.expert.databinding.FragmentDesignAddBinding
import com.oldee.expert.databinding.LayoutDesignAddImageBinding
import com.oldee.expert.retrofit.response.DesignDetailResponse
import com.oldee.expert.ui.dialog.PrepareItemDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val IMAGE_ITEM = 1
const val IMAGE_BEFORE = 2
const val IMAGE_AFTER = 3
const val MODE_WRITE = 0
const val MODE_MODIFY = 1

const val IMAGE_URI = 0
const val IMAGE_SERVER = 1
const val IMAGE_SCOPE_STORAGE = 3

//10298118
const val IMAGE_MAX_SIZE = 20000000

@AndroidEntryPoint
class DesignAddFragment :
    BaseFragment<FragmentDesignAddBinding, DesignAddViewModel, DesignAddFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_design_add
    override val viewModel: DesignAddViewModel by viewModels()
    override val navArgs: DesignAddFragmentArgs by navArgs()

    var currentPhotoPath: String? = null
    var photoUri: Uri? = null

    val bindingImageView = mutableListOf<LayoutDesignAddImageBinding>()

    //old
    private lateinit var prepareItemAdapter: PrepareItemRecyclerViewAdapter
    private lateinit var bluePrintAdapter: DesignBluePrintImageAdapter

    private val pictureActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        pushImageData(IMAGE_ITEM, it.resultCode, it.data)
    }

    private val beforePictureActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        pushImageData(IMAGE_BEFORE, it.resultCode, it.data)
    }

    private val afterPictureActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        pushImageData(IMAGE_AFTER, it.resultCode, it.data)
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.mode = if (navArgs.id == 0) MODE_WRITE else MODE_MODIFY
        viewModel.reformId = navArgs.id

        binding.toolbar.tvTitle.text = "디자인 상품 등록"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

//        bluePrintAdapter = DesignBluePrintImageAdapter({
//            if (viewModel.getBluePrintImageCount() < viewModel.BLUE_IMAGE_MAX) {
//                showFileSelector(IMAGE_ITEM)
//            } else {
//                activityFuncFunction.showToast("이미지는 5개까지 첨부 가능합니다.")
//            }
//        }, {
//            viewModel.deleteBluePrintImage(it)
//        }, { v, d -> setImage(v, d) })
//        binding.rvBlue.adapter = bluePrintAdapter

        binding.llAddImage.setOnClickListener {
            showFileSelector(IMAGE_ITEM)
        }

        binding.glBlue.children.forEachIndexed { index, v ->
            if (index < 5) {
                bindingImageView.add(DataBindingUtil.bind(v)!!)
                bindingImageView[index].index = index
                bindingImageView[index].root.tag = bindingImageView[index]
            }
        }

        bindingImageView.forEach {
            it.btnDelete.setOnClickListener { v ->
                viewModel.deleteBluePrintImage(it.index ?: 0)
            }
        }

        binding.llAddBefore.setOnClickListener {
            showFileSelector(IMAGE_BEFORE)
        }

        binding.llAddAfter.setOnClickListener {
            showFileSelector(IMAGE_AFTER)
        }

        prepareItemAdapter = PrepareItemRecyclerViewAdapter(requireContext(), {
            showAddPrepareItem()
        }){
            viewModel.deletePrepareItem(it)
        }
        binding.rvPrepareItem.adapter = prepareItemAdapter

        binding.btnPost.setOnClickListener {
            if (viewModel.isValueValidated()) {
                val s = viewModel.price.value
                s?.let{ priceStr->
                    val intPrice = priceStr.toInt()

                    if(intPrice > MAX_PRICE){
                        activityFuncFunction.showToast("판매 금액이 천만 원을 넘는다면 관리자에게 문의해 주세요.")
                    }else if(intPrice < MIN_PRICE){
                        activityFuncFunction.showToast("판매 금액은 천 원을 이상이어야 합니다.")
                    }else{
                        viewModel.onClickPost(requireContext()) {
                            activityFuncFunction.showToast("용량이 큰 이미지 파일은 등록할 수 없어요.")
                        }
                    }
                }
            } else {
                activityFuncFunction.showToast("누락된 정보가 있습니다.")
            }
        }

        binding.data = viewModel
    }

    override fun initDataBinding() {
        viewModel.viewingBluePrintImage.observe(viewLifecycleOwner) {
            binding.llAddImage.isEnabled = it.count() != 5
            binding.btnAdd.isEnabled = it.count() != 5

            for (i in 0 until 5) {
                if (i < it.count()) {
                    setImage(bindingImageView[i].ivProduct, it[i])
                } else {
                    bindingImageView[i].ivProduct.setImageResource(0)
                }
            }
//            val viewList = mutableListOf<DesignBluePrintImageAdapter.AdapterImageData>()
//            it?.let { list ->
//                list.forEach { item ->
//                    val str = when (item.type) {
//                        IMAGE_SERVER -> item.path
//                        else -> item.uri.toString()
//                    }
//                    val d = DesignBluePrintImageAdapter.AdapterImageData(str ?: "")
//                    viewList.add(d)
//                }
//            }
//
////            viewList.add(DesignBluePrintImageAdapter.AdapterImageData("-1"))
////            bluePrintAdapter.submitList(viewList)
//            Log.e("#debug", "current list size: ${bluePrintAdapter.currentList.size}")
        }

        viewModel.beforeImagePath.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.ivBefore.setImageResource(0)
                binding.llAddBefore.visibility = View.VISIBLE
                binding.llDeleteBefore.visibility = View.GONE
            } else {
                setImage(binding.ivBefore, it)
//                Glide.with(requireActivity()).load(it.uri).into(binding.ivBefore)
                binding.llAddBefore.visibility = View.GONE
                binding.llDeleteBefore.visibility = View.VISIBLE
                binding.llDeleteBefore.setOnClickListener {
                    viewModel.deleteBeforeImage()
                }
            }
        }
        viewModel.afterImagePath.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.ivAfter.setImageResource(0)
                binding.llAddAfter.visibility = View.VISIBLE
                binding.llDeleteAfter.visibility = View.GONE
            } else {
                setImage(binding.ivAfter, it)
//                Glide.with(requireActivity()).load(it.uri).into(binding.ivAfter)
                binding.llAddAfter.visibility = View.GONE
                binding.llDeleteAfter.visibility = View.VISIBLE
                binding.llDeleteAfter.setOnClickListener {
                    viewModel.deleteAfterImage()
                }
            }
        }
        viewModel.prepareItemList.observe(viewLifecycleOwner) {
            (binding.rvPrepareItem.adapter as PrepareItemRecyclerViewAdapter).submitList(it?.toMutableList())
        }
        viewModel.postResult.observe(viewLifecycleOwner) {
            it?.let { res ->
                if (res.errorMessage == null) {
                    Log.e("#debug", "post success")
                    navController?.previousBackStackEntry?.savedStateHandle?.set("post_design", "success")
                    navController?.popBackStack()
                } else {
                    activityFuncFunction.showToast("에러")
                }
            }
        }

        viewModel.minDay.observe(viewLifecycleOwner) {
            Log.e("#debug", it)
        }

        viewModel.oldData.observe(viewLifecycleOwner) {
            it?.let {
                setOldDesign(it)
            }
        }
    }

    override fun initAfterBinding() {
        if (viewModel.mode == MODE_MODIFY) {
            viewModel.requestOldDesign(viewModel.reformId)
        }
    }

    private fun setOldDesign(data: DesignDetailResponse.DesignDetailData) {
        val beforeImage = data.beforeImageName
        val afterImage = data.afterImageName
        val blueImage = data.images

        viewModel.reformName.postValue(data.reformName)
        viewModel.price.postValue(data.price.toString())
        viewModel.contents.postValue(data.contents)
        viewModel.minDay.postValue(data.minDay.toString())
        viewModel.maxDay.postValue(data.maxDay.toString())

        viewModel.beforeImagePath.postValue(
            DesignAddViewModel.ImageData(
                IMAGE_SERVER,
                beforeImage,
                null
            )
        )
        viewModel.afterImagePath.postValue(
            DesignAddViewModel.ImageData(
                IMAGE_SERVER,
                afterImage,
                null
            )
        )
        viewModel.addBluePrintImageServer(blueImage)

        //set prepareItem
        val prepareItems = data.items
        prepareItems.forEach {
            viewModel.addPrepareItem(
                PrepareItemRecyclerViewAdapter.PrepareItem(
                    it.itemCode,
                    it.itemName
                )
            )
        }
    }

    fun setImage(view: ImageView, data: DesignAddViewModel.ImageData) {
        if (data.type == IMAGE_URI && data.uri != null) {
            Glide.with(requireActivity()).load(data.uri)
                .into(view)
        } else {
            viewModel.setImage(view, data.path ?: "")
        }
    }

    fun showAddPrepareItem() {
        PrepareItemDialogFragment { code, name ->
            activityFuncFunction.hideSoftKeyboard()
            viewModel.addPrepareItem(PrepareItemRecyclerViewAdapter.PrepareItem(code.value, name))
        }.show(parentFragmentManager, "add_prepare_item")
    }

    fun showFileSelector(clickType: Int) {
        val state = Environment.getExternalStorageState()
        if (!TextUtils.equals(state, Environment.MEDIA_MOUNTED))
            return

        //create camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        //add image capture event
        cameraIntent.resolveActivity(requireContext().packageManager)?.let {
            createImageFile()?.let { f ->
                photoUri = FileProvider.getUriForFile(
                    this.requireContext(),
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    f
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                cameraIntent.putExtra("mode", 0)
            }
        }

        //create galley intent
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
//            type = "image/jpeg"
            data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            putExtra(Intent.EXTRA_MIME_TYPES, "image/jpeg")
        }

        Intent.createChooser(intent, "사진 업로드 방법 선택").run {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
            putExtra("click", clickType)
            when (clickType) {
                IMAGE_ITEM -> {
                    pictureActivityResultLauncher.launch(this)
                }
                IMAGE_BEFORE -> {
                    beforePictureActivityResultLauncher.launch(this)
                }
                IMAGE_AFTER -> {
                    afterPictureActivityResultLauncher.launch(this)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    fun pushImageData(clickType: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val list = mutableListOf<Uri>()
            //use camera
            if (data == null) {
                list.add(Uri.fromFile(File(currentPhotoPath ?: "")))
            } else {
                val selectedImage = data.data

                selectedImage?.let {
                    val path = it.path
                    val mimeType = it.let {
                        returnUri-> context?.contentResolver?.getType(it)
                    }

                    if(mimeType == "image/jpeg"){
                        list.add(it)
                    }else{
                        activityFuncFunction.showToast("JPG 이미지만 등록할 수 있어요.")
                    }
                }
            }

            if (list.isEmpty()) {
                Log.e("#debug", "image list is null")
                return
            }

            when (clickType) {
                IMAGE_ITEM -> {
                    val oldList = viewModel.getOfflineImageUriList(requireContext())

                    var oldSize = 0L
                    oldList.forEach {
                        oldSize += viewModel.getFileSize(requireContext(), it)
                    }

                    val currentSize = viewModel.getFileSize(requireContext(), list[0])

                    Log.e("#debug", "size:${oldSize+currentSize}")

                    if(currentSize+oldSize > IMAGE_MAX_SIZE){
                        activityFuncFunction.showToast("용량이 큰 이미지 파일은 등록할 수 없어요.")
                    }else{
                        viewModel.addBluePrintImageList(list[0]){
                            activityFuncFunction.showToast("동일한 이미지가 있습니다.")
                        }
                    }
                }
                IMAGE_BEFORE -> {
                    val currentSize = viewModel.getFileSize(requireContext(), list[0])
                    if(currentSize > IMAGE_MAX_SIZE){
                        activityFuncFunction.showToast("용량이 큰 이미지 파일은 등록할 수 없어요.")
                    }else{
                        viewModel.addBeforeImage(list[0])
                    }
                }
                IMAGE_AFTER -> {
                    val currentSize = viewModel.getFileSize(requireContext(), list[0])
                    if(currentSize > IMAGE_MAX_SIZE){
                        activityFuncFunction.showToast("용량이 큰 이미지 파일은 등록할 수 없어요.")
                    }else{
                        viewModel.addAfterImage(list[0])
                    }
                }
            }
        }
    }
}