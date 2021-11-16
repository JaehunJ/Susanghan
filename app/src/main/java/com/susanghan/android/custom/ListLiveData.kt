package com.susanghan.android.custom

import androidx.lifecycle.MutableLiveData

class ListLiveData<T> : MutableLiveData<MutableList<T>>() {
    val temp = mutableListOf<T>()

    init {
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