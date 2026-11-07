package com.money.categories.di

import com.money.categories.data.repository.CategoryExpenseRepositoryImpl
import com.money.categories.data.repository.CategoryIncomeRepositoryImpl
import com.money.categories.data.repository.CategoryExpenseRepository
import com.money.categories.data.repository.CategoryIncomeRepository
import com.money.categories.domain.use_cases.AddUserExpenseCategoryUseCase
import com.money.categories.domain.use_cases.AddUserExpenseCategoryUseCaseImpl
import com.money.categories.domain.use_cases.AddUserIncomeCategoryUseCase
import com.money.categories.domain.use_cases.AddUserIncomeCategoryUseCaseImpl
import com.money.categories.domain.use_cases.DeleteUserExpenseCategoryUseCase
import com.money.categories.domain.use_cases.DeleteUserExpenseCategoryUseCaseImpl
import com.money.categories.domain.use_cases.DeleteUserIncomeCategoryUseCase
import com.money.categories.domain.use_cases.DeleteUserIncomeCategoryUseCaseImpl
import com.money.categories.domain.use_cases.GetExpenseCategoriesUseCase
import com.money.categories.domain.use_cases.GetExpenseCategoriesUseCaseImpl
import com.money.categories.domain.use_cases.GetIncomeCategoriesUseCase
import com.money.categories.domain.use_cases.GetIncomeCategoriesUseCaseImpl
import com.money.categories.domain.use_cases.UpdateUserExpenseCategoryUseCase
import com.money.categories.domain.use_cases.UpdateUserExpenseCategoryUseCaseImpl
import com.money.categories.domain.use_cases.UpdateUserIncomeCategoryUseCase
import com.money.categories.domain.use_cases.UpdateUserIncomeCategoryUseCaseImpl
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

    @Binds
    abstract fun bindsAddUserExpenseCategoryUseCase(impl: AddUserExpenseCategoryUseCaseImpl): AddUserExpenseCategoryUseCase

    @Binds
    abstract fun bindsAddUserIncomeCategoryUseCase(impl: AddUserIncomeCategoryUseCaseImpl): AddUserIncomeCategoryUseCase

    @Binds
    abstract fun bindsDeleteUserExpenseCategoryUseCase(impl: DeleteUserExpenseCategoryUseCaseImpl): DeleteUserExpenseCategoryUseCase

    @Binds
    abstract fun bindsDeleteUserIncomeCategoryUseCase(impl: DeleteUserIncomeCategoryUseCaseImpl): DeleteUserIncomeCategoryUseCase

    @Binds
    abstract fun bindsGetExpenseCategoriesUseCase(impl: GetExpenseCategoriesUseCaseImpl): GetExpenseCategoriesUseCase

    @Binds
    abstract fun bindsGetIncomeCategoriesUseCase(impl: GetIncomeCategoriesUseCaseImpl): GetIncomeCategoriesUseCase

    @Binds
    abstract fun bindsUpdateUserExpenseCategoryUseCase(impl: UpdateUserExpenseCategoryUseCaseImpl): UpdateUserExpenseCategoryUseCase

    @Binds
    abstract fun bindsUpdateUserIncomeCategoryUseCase(impl: UpdateUserIncomeCategoryUseCaseImpl): UpdateUserIncomeCategoryUseCase
}
