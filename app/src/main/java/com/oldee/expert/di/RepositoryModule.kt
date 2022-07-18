package com.oldee.expert.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.oldee.expert.BuildConfig
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.repository.*
import com.oldee.expert.retrofit.SusanghanService
import com.oldee.expert.ui.CommonActivityFuncImpl
import com.oldee.expert.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideServerRepository(
        api: SusanghanService,
        preferences: SharedPreferences,
    ) =
        ServerRepository(api, preferences)

    @Singleton
    @Provides
    fun provideEtcServiceRepository(
        api: SusanghanService,
        preferences: SharedPreferences,
    ) =
        EtcServiceRepository(api, preferences)

    @Singleton
    @Provides
    fun provideSignInRepository(
        api: SusanghanService,
        preferences: SharedPreferences
    ) =
        SignInRepository(api, preferences)

    @Singleton
    @Provides
    fun provideSignUpRepository(
        api: SusanghanService,
        preferences: SharedPreferences
    ) =
        SignUpRepository(api, preferences)

    @Singleton
    @Provides
    fun provideOrderListRepository(
        api: SusanghanService,
        preferences: SharedPreferences
    ) =
        OrderListRepository(api, preferences)

    @Singleton
    @Provides
    fun provideDesignRepository(
        api: SusanghanService,
        preferences: SharedPreferences
    ) =
        DesignRepository(api, preferences)

    @Singleton
    @Provides
    fun provideBaseRepository(
        api: SusanghanService,
        preferences: SharedPreferences
    ) =
        BaseRepository(api, preferences)

    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)

//    @Singleton
//    @Provides
//    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context) = try {
//        EncryptedSharedPreferences.create(
//            context.getString(R.string.app_name),
//            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    } catch (e: Exception) {
//        null
//    }
}