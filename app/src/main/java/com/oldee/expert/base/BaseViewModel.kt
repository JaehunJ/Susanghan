package com.oldee.expert.base

import android.graphics.Rect
import android.util.Size
import android.widget.ImageView
import androidx.annotation.Dimension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oldee.expert.R
import com.oldee.expert.retrofit.NoConnectionInterceptor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel(var repository: BaseRepository) : ViewModel() {
    fun isLoading() = repository.getIsLoading()

    fun hasError() = repository.getHasError()

    val connectionExceptionHandler = CoroutineExceptionHandler { _, e ->
        isLoading().postValue(false)
        if(e is NoConnectionInterceptor.NoConnectivityException) hasError().postValue(true)
    }

    fun remote(useProgressBar: Boolean = true, action: suspend () -> Unit) {
        viewModelScope.launch(connectionExceptionHandler) {
            isLoading().postValue(true)

            action()

            isLoading().postValue(false)
        }
    }

    fun postDelay(action: () -> Unit, milisec: Long) {
        viewModelScope.launch {
            delay(milisec)
            action()
        }
    }

    fun setImage(imageView: ImageView, url: String, size:Size? = null) {
        viewModelScope.launch(connectionExceptionHandler) {
            val bitmap = if (url.isNotEmpty()) repository.getImageFromServer(url) else null
            if (bitmap != null) {
                if(size == null){
                    Glide.with(imageView.context).load(bitmap).placeholder(R.drawable.icon_empty_image)
                        .error(R.drawable.icon_empty_image).into(imageView)
                }else{

                    Glide.with(imageView.context).load(bitmap).placeholder(R.drawable.icon_empty_image).override(size.width, size.height)
                        .error(R.drawable.icon_empty_image).into(imageView)
                }
            } else {
                Glide.with(imageView.context).load(R.drawable.icon_empty_image).into(imageView)
            }
        }
    }

    fun setImageCircle(imageView: ImageView, url: String, size:Size? = null) {
        viewModelScope.launch(connectionExceptionHandler) {
            val bitmap = if (url.isNotEmpty()) repository.getImageFromServer(url) else null
            if (bitmap != null) {
                if(size == null){
                    Glide.with(imageView.context).load(bitmap).apply(RequestOptions().circleCrop())
                        .placeholder(R.drawable.icon_empty_image).error(R.mipmap.ic_launcher_round)
                        .into(imageView)
                }else{
                    Glide.with(imageView.context).load(bitmap).apply(RequestOptions().circleCrop())
                        .placeholder(R.drawable.icon_empty_image).error(R.mipmap.ic_launcher_round).override(size.width, size.height)
                        .into(imageView)
                }
            } else {
                Glide.with(imageView.context).load(R.mipmap.ic_launcher_round)
                    .apply(RequestOptions().circleCrop())
                    .into(imageView)
            }
        }
    }
}