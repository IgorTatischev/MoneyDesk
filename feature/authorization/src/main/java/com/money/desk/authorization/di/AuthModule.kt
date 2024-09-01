package com.money.desk.authorization.di

import com.money.common.PasswordValidator
import com.money.desk.authorization.data.firebase.AuthRepositoryImpl
import com.money.desk.authorization.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthBindModule {

    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}

@Module
@InstallIn(SingletonComponent::class)
internal class AuthModule  {

    @Provides
    @Singleton
    fun providePasswordValidator() = PasswordValidator()
}