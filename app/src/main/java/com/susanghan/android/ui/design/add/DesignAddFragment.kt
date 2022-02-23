package com.susanghan.android.ui.design.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.susanghan.android.BuildConfig
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentDesignAddBinding
import com.susanghan.android.databinding.LayoutDesignAddImageBinding
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
        binding.llAddImage.setOnClickListener {
            showFileSelector(IMAGE_ITEM)
        }

        binding.gridImage.children.forEachIndexed {index, v->
            if(index < 5)
                bindingImageView.add(LayoutDesignAddImageBinding.bind(v))
        }
    }

    override fun initDataBinding() {
        viewModel.bluePrintImagePath.observe(viewLifecycleOwner){
            it.forEachIndexed {index, iter->
                Glide.with(requireActivity()).load(iter)
                    .into(bindingImageView[index].ivProduct)
            }
        }
    }

    override fun initAfterBinding() {

    }

    fun addImage() {

    }

    fun showFileSelector(clickType:Int) {
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

//        launcher.launch(cameraIntent)

        //create galley intent
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
            data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) //이미지 여러개 선택가능하도록 변경
        }
//
//        //create file chooser intent
        Intent.createChooser(intent, "사진 업로드 방법 선택").run {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
            putExtra("click", clickType)
            pictureActivityResultLauncher.launch(this)
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

    fun pushImageData(clickType:Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            var imageData = data
            var list = mutableListOf<Uri>()
            //use camera
            if(imageData == null){
                list.add(Uri.fromFile(File(currentPhotoPath?:"")))
            }else{
                val clipData = imageData.data
                clipData?.let{
                    list.add(it)
                }
//                list.add(clipData)
//
//                clipData?.let{clip->
//                    val cnt = clip.itemCount
//
//                    for(i in 0 until  cnt){
//                        list.add(clip.getItemAt(i).uri)
//                    }
//                }
            }

            if(list.isEmpty()){
                Log.e("#debug", "image list is null")
                return
            }

            when(clickType){
                IMAGE_ITEM->{
                    viewModel.addBluePrintImage(list)
                }
                IMAGE_BEFORE->{
                    viewModel.addBeforeImage(list[0])
                }
                IMAGE_AFTER->{
                    viewModel.addAfterImage(list[0])
                }
            }
        }
    }
}