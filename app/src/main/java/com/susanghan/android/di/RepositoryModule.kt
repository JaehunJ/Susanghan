package com.susanghan.android.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.susanghan.android.BuildConfig
import com.susanghan.android.repository.OrderListRepository
import com.susanghan.android.repository.SignInRepository
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
    fun provideSignInRepository(api:SusanghanService, preferences: SharedPreferences) = SignInRepository(api, preferences)

    @Singleton
    @Provides
    fun provideOrderListRepository(api: SusanghanService, preferences: SharedPreferences) = OrderListRepository(api, preferences)

    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
}