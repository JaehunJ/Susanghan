package com.oldee.expert.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer


fun <T> getObserver(owner: LifecycleOwner, action: (T) -> Unit): Observer<T> {
    return Observer<T> {
        if (owner.lifecycle.currentState == Lifecycle.State.RESUMED) action.invoke(
            it
        )
    }
}