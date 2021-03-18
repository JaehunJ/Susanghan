package com.susanghan.android.ui

import android.util.Log

class InjectCountData {
    companion object{
        var injectCount = 0
    }

    init {
        injectCount++
    }

    fun printCount(){
        Log.e("#debug", "injectCount $injectCount")
    }
}