package com.susanghan.android.repository

sealed class Resorces<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resorces<T>(data)
    class Erorr<T>(message: String, data: T? = null) : Resorces<T>(data, message)
}