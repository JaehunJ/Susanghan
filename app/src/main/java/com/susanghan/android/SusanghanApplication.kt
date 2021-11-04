package com.susanghan.android

import android.app.Application
import android.content.Context
import com.susanghan.android.di.apiModule
import com.susanghan.android.di.appModule
import com.susanghan.android.di.retrofitModule
import com.susanghan.android.di.viewModelModule
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
            modules(appModule, retrofitModule, apiModule, viewModelModule)
        }
    }
}