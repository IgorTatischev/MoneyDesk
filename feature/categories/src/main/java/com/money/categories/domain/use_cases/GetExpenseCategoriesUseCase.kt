package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.categories.domain.repository.CategoryExpenseRepository
import com.money.common.Resource
import com.money.common.usecases.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class GetExpenseCategoriesUseCase @Inject constructor(
    private val categoryExpenseRepository: CategoryExpenseRepository,
) : BaseUseCase {

    operator fun invoke(): Flow<Resource<List<CategoryModel>>> =
        combine(
            categoryExpenseRepository.getDefaultExpenseCategories(),
            categoryExpenseRepository.getUserExpenseCategories(),
        ) { defaultResult, userResult ->
            when {
                defaultResult is Resource.Error -> Resource.Error(message = defaultResult.message ?: "Unknown error")
                userResult is Resource.Error -> Resource.Error(message = userResult.message ?: "Unknown error")
                defaultResult is Resource.Success && userResult is Resource.Success ->
                    Resource.Success((defaultResult.data ?: emptyList()) + (userResult.data ?: emptyList()))

                else -> Resource.Loading()
            }
        }.flowOn(Dispatchers.IO)
}
