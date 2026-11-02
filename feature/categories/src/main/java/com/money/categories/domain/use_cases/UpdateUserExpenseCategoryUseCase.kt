package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.categories.domain.repository.CategoryExpenseRepository
import com.money.common.Resource
import com.money.common.usecases.BaseUseCase
import javax.inject.Inject

internal class UpdateUserExpenseCategoryUseCase @Inject constructor(
    private val categoryExpenseRepository: CategoryExpenseRepository,
) : BaseUseCase {

    suspend operator fun invoke(categoryId: String, category: CategoryModel): Resource<Unit> =
        categoryExpenseRepository.updateUserExpenseCategory(categoryId, category)
}
