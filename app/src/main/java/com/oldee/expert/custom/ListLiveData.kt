package com.oldee.expert.custom

import androidx.lifecycle.MutableLiveData

class ListLiveData<T> : MutableLiveData<MutableList<T>>() {
    private var temp = mutableListOf<T>()

    init {
        value = temp
    }

    fun getItemCount() = value?.count()?:0

    fun replace(items:List<T>){
        temp = items.toMutableList()
        value = temp
    }

    fun add(item: T) {
        temp.add(item)
        value = temp
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