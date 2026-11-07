package com.money.categories.domain.use_cases

import com.money.common.Resource
import com.money.common.usecases.BaseUseCase

interface DeleteUserExpenseCategoryUseCase : BaseUseCase {

    suspend operator fun invoke(categoryId: String): Resource<Unit>
}
