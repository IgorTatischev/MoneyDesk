package com.money.categories.domain.use_cases

import com.money.categories.data.repository.CategoryExpenseRepository
import com.money.common.Resource
import javax.inject.Inject

internal class DeleteUserExpenseCategoryUseCaseImpl @Inject constructor(
    private val categoryExpenseRepository: CategoryExpenseRepository,
) : DeleteUserExpenseCategoryUseCase {

    override suspend operator fun invoke(categoryId: String): Resource<Unit> =
        categoryExpenseRepository.deleteUserExpenseCategory(categoryId)
}
