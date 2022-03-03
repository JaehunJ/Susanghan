package com.susanghan.android.retrofit.request

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File

class UploadRequestBody(
    val file: File, val mediaType: MediaType
) : RequestBody() {
    override fun contentType(): MediaType = mediaType

    override fun contentLength(): Long = file.length()

    override fun writeTo(sink: BufferedSink) {
//        val len  =
    }
}