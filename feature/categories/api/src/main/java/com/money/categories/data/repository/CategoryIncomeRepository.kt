package com.money.categories.data.repository

import com.money.categories.domain.model.CategoryModel
import com.money.common.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryIncomeRepository {

    fun getDefaultIncomeCategories(): Flow<Resource<List<CategoryModel>>>

    fun getUserIncomeCategories(): Flow<Resource<List<CategoryModel>>>

    suspend fun addUserIncomeCategory(category: CategoryModel): Resource<Unit>

    suspend fun deleteUserIncomeCategory(categoryId: String): Resource<Unit>

    suspend fun updateUserIncomeCategory(categoryId: String, category: CategoryModel): Resource<Unit>
}