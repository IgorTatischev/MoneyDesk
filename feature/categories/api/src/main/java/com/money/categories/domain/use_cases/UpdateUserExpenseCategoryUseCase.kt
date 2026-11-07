package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.common.Resource
import com.money.common.usecases.BaseUseCase

interface UpdateUserExpenseCategoryUseCase : BaseUseCase {

    suspend operator fun invoke(categoryId: String, category: CategoryModel): Resource<Unit>
}
