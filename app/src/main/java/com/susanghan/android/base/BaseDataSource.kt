package com.susanghan.android.base

import com.susanghan.android.retrofit.response.BaseResponse
import retrofit2.Response


abstract class BaseDataSource {
    abstract fun <T> getResult() : T
}