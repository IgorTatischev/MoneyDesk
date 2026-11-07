package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.categories.data.repository.CategoryExpenseRepository
import com.money.common.Resource
import javax.inject.Inject

internal class UpdateUserExpenseCategoryUseCaseImpl @Inject constructor(
    private val categoryExpenseRepository: CategoryExpenseRepository,
) : UpdateUserExpenseCategoryUseCase {

    override suspend operator fun invoke(categoryId: String, category: CategoryModel): Resource<Unit> =
        categoryExpenseRepository.updateUserExpenseCategory(categoryId, category)
}
