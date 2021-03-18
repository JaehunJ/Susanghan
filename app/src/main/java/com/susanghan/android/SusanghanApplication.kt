package com.susanghan.android

import android.app.Application
import android.content.Context
import com.susanghan.android.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

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

        startKoin {
            androidLogger()
            androidContext(this@SusanghanApplication)
            fragmentFactory()
            modules(myModule)
        }
    }
}