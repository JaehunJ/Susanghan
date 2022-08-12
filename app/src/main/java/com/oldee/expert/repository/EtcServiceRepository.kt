package com.oldee.expert.repository

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Size
import android.widget.ImageView
import com.oldee.expert.base.BaseRepository
import com.oldee.expert.retrofit.SusanghanService

class EtcServiceRepository(
    api: SusanghanService,
    prefs: SharedPreferences,
) : BaseRepository(api, prefs) {
    suspend fun requestNotice(page: Int) = call {
        api.requestNotice(getAccessToken(), page)
    }

    suspend fun requestFaq(page: Int, limit: Int) = call {
        api.requestFaq(getAccessToken(), page, limit)
    }

    suspend fun requestImageFromServer(url: String):Bitmap?{
        val result = api.requestImage(getAccessToken(), url).body()

        if (result != null) {
            return BitmapFactory.decodeStream(result.byteStream())
        }

        return null
    }
}