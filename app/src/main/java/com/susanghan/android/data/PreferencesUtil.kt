package com.susanghan.android.data

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesUtil {
    @Provides
    @Singleton
    fun providesSharedPreference(app: Application):
}