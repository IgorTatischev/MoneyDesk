package com.money.categories.di

import com.money.categories.data.repository.CategoryExpenseRepositoryImpl
import com.money.categories.data.repository.CategoryIncomeRepositoryImpl
import com.money.categories.domain.repository.CategoryExpenseRepository
import com.money.categories.domain.repository.CategoryIncomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CategoriesBindModule {

    @Binds
    abstract fun bindsCategoryIncomeRepository(impl: CategoryIncomeRepositoryImpl): CategoryIncomeRepository

    @Binds
    abstract fun bindsCategoryExpenseRepository(impl: CategoryExpenseRepositoryImpl): CategoryExpenseRepository
}
