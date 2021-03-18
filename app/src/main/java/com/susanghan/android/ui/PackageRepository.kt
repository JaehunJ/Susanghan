package com.susanghan.android.ui

import android.content.Context

class PackageRepository(context: Context) {
    val packageName:String
    init {

        packageName = context.packageName
    }
}