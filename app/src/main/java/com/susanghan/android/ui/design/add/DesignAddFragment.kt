package com.susanghan.android.ui.design.add

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
import com.susanghan.android.BuildConfig
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentDesignAddBinding
import com.susanghan.android.databinding.LayoutDesignAddImageBinding
import com.susanghan.android.retrofit.response.DesignDetailResponse
import com.susanghan.android.ui.dialog.PrepareItemDialogFragment
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

@AndroidEntryPoint
class DesignAddFragment :
    BaseFragment<FragmentDesignAddBinding, DesignAddViewModel, DesignAddFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_design_add
    override val viewModel: DesignAddViewModel by viewModels()
    override val navArgs: DesignAddFragmentArgs by navArgs()

    var currentPhotoPath: String? = null
    var photoUri: Uri? = null

    val bindingImageView = mutableListOf<LayoutDesignAddImageBinding>()
    private lateinit var prepareItemAdapter: PrepareItemRecyclerViewAdapter

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

        binding.toolbar.tvTitle.text = "디자인 상세"

        binding.llAddImage.setOnClickListener {
            showFileSelector(IMAGE_ITEM)
        }

        binding.gridImage.children.forEachIndexed { index, v ->
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

        prepareItemAdapter = PrepareItemRecyclerViewAdapter(requireContext()) {
            showAddPrepareItem()
        }
        binding.rvPrepareItem.adapter = prepareItemAdapter

        binding.btnPost.setOnClickListener {
            if (viewModel.isValueValidated()) {
                viewModel.onClickPost(requireContext())
            } else {
                activityFuncFunction.showToast("누락된 정보가 있습니다.")
            }
        }

        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
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
        }

        viewModel.beforeImagePath.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.ivBefore.setImageResource(0)
                binding.llAddBefore.visibility = View.VISIBLE
            } else {
                Glide.with(requireActivity()).load(it.uri).into(binding.ivBefore)
                binding.llAddBefore.visibility = View.GONE
            }
        }
        viewModel.afterImagePath.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.ivAfter.setImageResource(0)
                binding.llAddAfter.visibility = View.VISIBLE
            } else {
                Glide.with(requireActivity()).load(it).into(binding.ivAfter)
                binding.llAddAfter.visibility = View.GONE
            }
        }
        viewModel.prepareItemList.observe(viewLifecycleOwner) {
            (binding.rvPrepareItem.adapter as PrepareItemRecyclerViewAdapter).submitList(it?.toMutableList())
        }
        viewModel.postResult.observe(viewLifecycleOwner) {
            it?.let { res ->
                if (res.errorMessage == null) {
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
                    path?.let { p ->
                        val extension = p.contains("gif")

                        if (!extension) {
                            list.add(it)
                        } else {
                            activityFuncFunction.showToast("gif파일은 선택하실수 없습니다.")
                        }
                    }
                }
            }

            if (list.isEmpty()) {
                Log.e("#debug", "image list is null")
                return
            }

            when (clickType) {
                IMAGE_ITEM -> {
                    viewModel.addBluePrintImageList(list)
                }
                IMAGE_BEFORE -> {
                    viewModel.addBeforeImage(list[0])
                }
                IMAGE_AFTER -> {
                    viewModel.addAfterImage(list[0])
                }
            }
        }
    }
}