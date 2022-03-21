package com.susanghan.android.base

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(var repository: BaseRepository) : ViewModel() {
    fun isLoading() = repository.getIsLoading()

    fun setImage(imageView: ImageView, url: String) {
        viewModelScope.launch {
            val bitmap = repository.getImageFromServer(url)
            if (bitmap != null)
                imageView.setImageBitmap(bitmap)
        }
    }
}