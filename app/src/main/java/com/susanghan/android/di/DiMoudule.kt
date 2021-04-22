package com.susanghan.android.di

import com.susanghan.android.retrofit.SusanghanApi
import com.susanghan.android.retrofit.SusanghanService
import com.susanghan.android.ui.signin.SignInViewModel
import com.susanghan.android.ui.splash.SplashViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        SplashViewModel()
    }
    viewModel {
        SignInViewModel()
    }
//    single {
//        PackageRepository(androidContext())
//
//        single {
//            PrintService(get())
//        }
//
//        factory {
//            InjectCountData()
//        }
//    }
}

val appModule = module {

}

val retrofitModule = module {
    single<SusanghanApi> { provideRetrofitClient()}
}

private fun provideRetrofitClient()= Retrofit.Builder()
    .baseUrl("")
    .client(OkHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(SusanghanApi::class.java)