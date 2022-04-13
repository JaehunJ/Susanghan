package com.susanghan.android.repository

import android.content.SharedPreferences
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.retrofit.SusanghanService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerRepository @Inject constructor(api: SusanghanService, prefs: SharedPreferences):BaseRepository(api, prefs){

    suspend fun requestVersionInfo() = call{api.requestVersionInfo()}
}