package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.common.Resource
import com.money.common.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow

interface GetExpenseCategoriesUseCase : BaseUseCase {

    operator fun invoke(): Flow<Resource<List<CategoryModel>>>
}
