package com.susanghan.android.custom

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 *
 *
 * @param context
 * @param permissions
 * @return
 */
fun checkPermission(context: Context, vararg permissions: String) = permissions.all {
    ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}

fun getImageBody(key: String, file: File) = MultipartBody.Part.createFormData(
    name = key,
    filename = file.name,
    body = file.asRequestBody("image/*".toMediaType())
)

fun getImageBody(key: String, files: List<File>): List<MultipartBody.Part> {
    val list = arrayListOf<MultipartBody.Part>()

    files.forEach {
        val body = getImageBody(key, it)
        list.add(body)
    }

    return list
}

fun getImageBodyUri(key: String, uries: List<Uri>): List<MultipartBody.Part> {
    val list = arrayListOf<MultipartBody.Part>()

    uries.forEach {
        val body = getImageBody(key, File(it.path ?: ""))
        list.add(body)
    }

    return list
}