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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.susanghan.android.BuildConfig
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentDesignAddBinding
import com.susanghan.android.databinding.LayoutDesignAddImageBinding
import com.susanghan.android.ui.dialog.PrepareItemDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val IMAGE_ITEM = 1
const val IMAGE_BEFORE = 2
const val IMAGE_AFTER = 3

@AndroidEntryPoint
class DesignAddFragment : BaseFragment<FragmentDesignAddBinding, DesignAddViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_design_add
    override val viewModel: DesignAddViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    var currentPhotoPath: String? = null
    var photoUri: Uri? = null

    val bindingImageView = mutableListOf<LayoutDesignAddImageBinding>()
    private lateinit var prepareItemAdapter:PrepareItemRecyclerViewAdapter

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

        prepareItemAdapter = PrepareItemRecyclerViewAdapter(requireContext()){
            showAddPrepareItem()
        }
        binding.rvPrepareItem.adapter = prepareItemAdapter

        binding.btnPost.setOnClickListener {
            viewModel.onClickPost(requireContext())
        }

        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun initDataBinding() {
        viewModel.bluePrintImagePath.observe(viewLifecycleOwner) {
            binding.llAddImage.isEnabled = it.count() != 5
            binding.btnAdd.isEnabled = it.count() != 5

            for (i in 0 until 5) {
                if (i < it.count()) {
                    Glide.with(requireActivity()).load(it[i])
                        .into(bindingImageView[i].ivProduct)
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
                Glide.with(requireActivity()).load(it).into(binding.ivBefore)
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
        viewModel.prepareItemList.observe(viewLifecycleOwner){
            (binding.rvPrepareItem.adapter as PrepareItemRecyclerViewAdapter).submitList(it?.toMutableList())
        }
        viewModel.postResult.observe(viewLifecycleOwner){
            it?.let{res->
                if(res.errorMessage == null){
                    navController?.popBackStack()
                }else{
                    activityFuncFunction.showToast("에러")
                }
            }
        }
    }

    override fun initAfterBinding() {

    }

    fun showAddPrepareItem(){
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
            when(clickType){
                IMAGE_ITEM->{
                    pictureActivityResultLauncher.launch(this)
                }
                IMAGE_BEFORE->{
                    beforePictureActivityResultLauncher.launch(this)
                }
                IMAGE_AFTER->{
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

                selectedImage?.let{
                    list.add(it)
                }
//                val clipData = data.clipData
//                clipData?.let { clips->
//                    val cnt = clips.itemCount
//                    for(i in 0 until cnt){
//                        list.add(clips.getItemAt(i).uri)
//                    }

//                }
            }

            if (list.isEmpty()) {
                Log.e("#debug", "image list is null")
                return
            }

            when (clickType) {
                IMAGE_ITEM -> {
                    viewModel.addBluePrintImage(list)
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