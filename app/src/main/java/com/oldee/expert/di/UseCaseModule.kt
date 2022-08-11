package com.oldee.expert.di

import com.oldee.expert.base.BaseRepository
import com.oldee.expert.repository.*
import com.oldee.expert.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetVersionInfoUseCase(repo:ServerRepository) = GetVersionInfoUseCase(repo)

    @Provides
    fun providePostSignInUseCase(repo:SignInRepository) = PostSignInUseCase(repo)

    @Provides
    fun provideSetUserStatusUseCase(repo:SignInRepository) = SetUserStatusUseCase(repo)

    @Provides
    fun provideGetNewTokenUseCase(repo:BaseRepository) = GetNewTokenUseCase(repo)

    @Provides
    fun providePostSignUpUseCase(repo:SignUpRepository) = PostSignUpUseCase(repo)

    @Provides
    fun provideGetStoreConfirmUseCase(repo:SignUpRepository) = GetStoreConfirmUseCase(repo)

    @Provides
    fun provideGetProfileUseCase(repo:SignInRepository) = GetProfileUseCase(repo)

    @Provides
    fun provideGetOrderListUseCase(repo:OrderListRepository) = GetOrderListUseCase(repo)

    @Provides
    fun provideGetOrderDetailUseCase(repo:OrderListRepository) = GetOrderDetailUseCase(repo)

    @Provides
    fun provideGetOrderCountUseCase(repo:OrderListRepository) = GetOrderCountUseCase(repo)

    @Provides
    fun providePostOrderStatusUseCase(repo:OrderListRepository) = PostOrderStatusChangeUseCase(repo)

    @Provides
    fun provideGetDesignListUseCase(repo:DesignRepository) = GetDesignListUseCase(repo)

    @Provides
    fun provideGetDesignDetailUseCase(repo:DesignRepository) = GetDesignDetailUseCase(repo)

    @Provides
    fun providePostDesignStatusChangeUseCase(repo:DesignRepository) = PostDesignStatusChangeUseCase(repo)

    @Provides
    fun providePostDesignAddUseCase(repo:DesignRepository) = PostDesignAddUseCase(repo)

    @Provides
    fun providePostDesignModifyUseCase(repo:DesignRepository) = PostDesignModifyUseCase(repo)

    @Provides
    fun provideGetFaqUseCase(repo:EtcServiceRepository) = GetFaqUseCase(repo)

    @Provides
    fun provideGetNoticeUseCase(repo:EtcServiceRepository) = GetNoticeUseCase(repo)

    @Provides
    fun provideGetImageUseCase(repo:BaseRepository) = GetImageUseCase(repo)

    @Provides
    fun providePostImageUseCase(repo:DesignRepository) = PostImageUseCase(repo)

    @Provides
    fun provideGetDeliveryUseCase(repo:OrderListRepository) = GetDeliveryListUseCase(repo)
}