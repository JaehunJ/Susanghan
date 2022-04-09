package com.susanghan.android.repository

import android.content.SharedPreferences
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.retrofit.SusanghanService

class EtcServiceRepository(
    api: SusanghanService,
    prefs: SharedPreferences
) : BaseRepository(api, prefs) {
    suspend fun requestNotice(page: Int) = call {
        api.requestNotice(getAccessToken(), page)
    }

    suspend fun requestFaq(page:Int, limit:Int) = call{
        api.requestFaq(getAccessToken(), page, limit)
    }
}