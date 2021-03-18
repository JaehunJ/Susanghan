package com.susanghan.android.di

import com.susanghan.android.ui.InjectCountData
import com.susanghan.android.ui.PackageRepository
import com.susanghan.android.ui.PrintService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val myModule = module {
    single {
        PackageRepository(androidContext())

        single {
            PrintService(get())
        }

        factory {
            InjectCountData()
        }
    }
}