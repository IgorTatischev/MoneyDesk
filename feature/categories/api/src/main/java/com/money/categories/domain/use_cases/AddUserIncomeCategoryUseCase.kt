package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.common.Resource
import com.money.common.usecases.BaseUseCase

interface AddUserIncomeCategoryUseCase : BaseUseCase {

    suspend operator fun invoke(category: CategoryModel): Resource<Unit>
}
