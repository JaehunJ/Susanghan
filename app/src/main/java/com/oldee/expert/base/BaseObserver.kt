package com.oldee.expert.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class BaseObserver<T>(val owner: LifecycleOwner, val action:()->Unit):Observer<T> {
    override fun onChanged(t: T) {

    }
}

fun <T> getObserver(owner:LifecycleOwner, action:(T)->Unit):Observer<T>{
    return Observer<T> { if(owner.lifecycle.currentState == Lifecycle.State.RESUMED) action.invoke(it)}
}