package com.oldee.expert.custom

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import kotlinx.parcelize.Parcelize

class ListLiveData<T> : MutableLiveData<MutableList<T>>() {
    var temp = mutableListOf<T>()

    init {
        value = temp
    }

    fun getItemCount() = value?.count() ?: 0

    fun replace(items: MutableList<T>) {
        temp = items
        value = temp
    }

    fun add(item: T) {
        temp.add(item)
    }

    fun add(items: List<T>) {
        temp.addAll(items)
        value = temp
    }

    fun remove(item: T) {
        temp.remove(item)
        value = temp
    }

    fun clear() {
        temp.clear()
        value = temp
    }
}