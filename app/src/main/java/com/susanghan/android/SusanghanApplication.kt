package com.susanghan.android

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class SusanghanApplication:Application() {

    companion object{
        private lateinit var instance:SusanghanApplication

        val context:Context
            get() = instance.applicationContext
    }

    init{
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }
}