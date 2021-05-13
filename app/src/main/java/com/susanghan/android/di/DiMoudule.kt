package com.susanghan.android.di

import com.susanghan.android.retrofit.SusanghanApi
import com.susanghan.android.ui.cs.CsViewModel
import com.susanghan.android.ui.more.MoreViewModel
import com.susanghan.android.ui.more.account.AccountViewModel
import com.susanghan.android.ui.more.account.withdraw.WithdrawViewModel
import com.susanghan.android.ui.more.adjustment.AdjustmentViewModel
import com.susanghan.android.ui.more.adjustment.detail.AdjustmentDetailViewModel
import com.susanghan.android.ui.more.faq.FaqViewModel
import com.susanghan.android.ui.more.list.NotiViewModel
import com.susanghan.android.ui.more.notice.UserNotificationViewModel
import com.susanghan.android.ui.more.list.detail.NotiDetailViewModel
import com.susanghan.android.ui.more.profile.ProfileDetailViewModel
import com.susanghan.android.ui.order.OrderViewModel
import com.susanghan.android.ui.order.detail.OrderDetailViewModel
import com.susanghan.android.ui.product.ProductViewModel
import com.susanghan.android.ui.signin.SignInViewModel
import com.susanghan.android.ui.signin.findid.FindIdResultViewModel
import com.susanghan.android.ui.signin.findid.FindIdViewModel
import com.susanghan.android.ui.signin.findpw.FindPwViewModel
import com.susanghan.android.ui.signin.findpw.SettingPwViewModel
import com.susanghan.android.ui.signup.SignUpPwViewModel
import com.susanghan.android.ui.signup.SignUpResultViewModel
import com.susanghan.android.ui.signup.SignUpViewModel
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
    viewModel {
        CsViewModel()
    }
    viewModel {
        AccountViewModel()
    }
    viewModel {
        WithdrawViewModel()
    }
    viewModel {
        AdjustmentViewModel()
    }
    viewModel {
        AdjustmentDetailViewModel()
    }
    viewModel {
        FaqViewModel()
    }
    viewModel {
        NotiViewModel()
    }
    viewModel {
        UserNotificationViewModel()
    }
    viewModel {
        NotiDetailViewModel()
    }
    viewModel {
        ProfileDetailViewModel()
    }
    viewModel {
        MoreViewModel()
    }
    viewModel {
        OrderViewModel()
    }
    viewModel {
        OrderDetailViewModel()
    }
    viewModel {
        ProductViewModel()
    }
    viewModel {
        FindIdViewModel()
    }
    viewModel {
        FindIdResultViewModel()
    }
    viewModel {
        FindPwViewModel()
    }
    viewModel {
        SettingPwViewModel()
    }
//    viewModel {
//        SignInViewModel()
//    }
    viewModel {
        SignUpViewModel()
    }
    viewModel {
        SignUpPwViewModel()
    }
    viewModel {
        SignUpResultViewModel()
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