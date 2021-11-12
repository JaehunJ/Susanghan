package com.susanghan.android.retrofit

import com.susanghan.android.BuildConfig
import com.susanghan.android.retrofit.request.SignInRequest
import com.susanghan.android.retrofit.response.BaseResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SusanghanApi{
    private const val CONNECT_TIMEOUT = 10L
    private const val WRITE_TIMEOUT = 1L
    private const val READ_TIMEOUT = 20L
    private const val BASE_URL = ""

    @Provides
    @Singleton
    fun provideHttpClient() = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SusanghanService::class.java)

    private fun <T> subscribe(
        flowable: Flowable<T>,
        onResponse: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        return flowable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onResponse(it)
            }) {
                onError(it)
            }
    }

    fun getOkHttpClient() : OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
//
//    @Provides
//    fun requestSignIn(id: String, pw: String, onResponse: (BaseResponse) -> Unit): Disposable {
//        return subscribe<BaseResponse>(api.requestSignIn(SignInRequest(id, pw)), onResponse, {
//
//        })
//    }
}