package com.oldee.expert.repository

import android.content.SharedPreferences
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.retrofit.SusanghanService

class EtcServiceRepository(
    api: SusanghanService,
    prefs: SharedPreferences,
    errorFragmentCallback:()->Unit
) : BaseRepository(api, prefs, errorFragmentCallback) {
    suspend fun requestNotice(page: Int) = call {
        api.requestNotice(getAccessToken(), page)
    }

    suspend fun requestFaq(page: Int, limit: Int) = call {
        api.requestFaq(getAccessToken(), page, limit)
    }
}