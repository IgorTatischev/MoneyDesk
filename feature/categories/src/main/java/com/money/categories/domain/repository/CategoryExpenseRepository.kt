package com.money.categories.domain.repository

import com.money.categories.domain.model.CategoryModel
import com.money.common.Resource
import kotlinx.coroutines.flow.Flow

internal interface CategoryExpenseRepository {

    fun getDefaultExpenseCategories(): Flow<Resource<List<CategoryModel>>>

    fun getUserExpenseCategories(): Flow<Resource<List<CategoryModel>>>

    suspend fun addUserExpenseCategory(category: CategoryModel): Resource<Unit>

    suspend fun deleteUserExpenseCategory(categoryId: String): Resource<Unit>

    suspend fun updateUserExpenseCategory(categoryId: String, category: CategoryModel): Resource<Unit>
}