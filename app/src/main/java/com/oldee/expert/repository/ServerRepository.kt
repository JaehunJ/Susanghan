package com.oldee.expert.repository

import android.content.SharedPreferences
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.retrofit.SusanghanService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerRepository @Inject constructor(api: SusanghanService, prefs: SharedPreferences):BaseRepository(api, prefs){

    suspend fun requestVersionInfo() = call{api.requestVersionInfo()}
}