package com.susanghan.android.ui

import android.util.Log

class PrintService(val packageRepository: PackageRepository) {
    fun printHello(){
        Log.v("#debug", "hello ${packageRepository.packageName}")
    }
}