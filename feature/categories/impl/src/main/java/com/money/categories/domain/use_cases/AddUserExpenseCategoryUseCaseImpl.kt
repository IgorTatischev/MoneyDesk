package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.categories.data.repository.CategoryExpenseRepository
import com.money.common.Resource
import javax.inject.Inject

internal class AddUserExpenseCategoryUseCaseImpl @Inject constructor(
    private val categoryExpenseRepository: CategoryExpenseRepository,
) : AddUserExpenseCategoryUseCase {

    override suspend operator fun invoke(category: CategoryModel): Resource<Unit> =
        categoryExpenseRepository.addUserExpenseCategory(category)
}
