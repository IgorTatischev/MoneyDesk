package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.categories.domain.repository.CategoryIncomeRepository
import com.money.common.Resource
import com.money.common.usecases.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class GetIncomeCategoriesUseCase @Inject constructor(
    private val categoryIncomeRepository: CategoryIncomeRepository,
) : BaseUseCase {

    operator fun invoke(): Flow<Resource<List<CategoryModel>>> =
        combine(
            categoryIncomeRepository.getDefaultIncomeCategories(),
            categoryIncomeRepository.getUserIncomeCategories(),
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
