package com.money.common.di

import com.money.common.PasswordValidator
import com.money.common.PasswordValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CommonBindModule {

    @Binds
    @Singleton
    abstract fun bindPasswordValidator(impl: PasswordValidatorImpl): PasswordValidator
}
