package com.money.di

import com.money.datastore.onboarding.OnBoardingDataStore
import com.money.datastore.onboarding.OnBoardingDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataStoreBindModule {

    @Binds
    abstract fun bindsOnboardingDatastore(onBoardingDataStore: OnBoardingDataStoreImpl): OnBoardingDataStore
}
