package com.susanghan.android.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.susanghan.android.BuildConfig
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.repository.*
import com.susanghan.android.retrofit.SusanghanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideServerRepository(api: SusanghanService, preferences: SharedPreferences) =
        ServerRepository(api, preferences)

    @Singleton
    @Provides
    fun provideEtcServiceRepository(api: SusanghanService, preferences: SharedPreferences) =
        EtcServiceRepository(api, preferences)

    @Singleton
    @Provides
    fun provideSignInRepository(api: SusanghanService, preferences: SharedPreferences) =
        SignInRepository(api, preferences)

    @Singleton
    @Provides
    fun provideSignUpRepository(api: SusanghanService, preferences: SharedPreferences) =
        SignUpRepository(api, preferences)

    @Singleton
    @Provides
    fun provideOrderListRepository(api: SusanghanService, preferences: SharedPreferences) =
        OrderListRepository(api, preferences)

    @Singleton
    @Provides
    fun provideDesignRepository(api: SusanghanService, preferences: SharedPreferences) =
        DesignRepository(api, preferences)

    @Singleton
    @Provides
    fun provideBaseRepository(api: SusanghanService, preferences: SharedPreferences) =
        BaseRepository(api, preferences)

    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
}