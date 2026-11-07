package com.money.categories.domain.use_cases

import com.money.categories.data.repository.CategoryIncomeRepository
import com.money.common.Resource
import javax.inject.Inject

internal class DeleteUserIncomeCategoryUseCaseImpl @Inject constructor(
    private val categoryIncomeRepository: CategoryIncomeRepository,
) : DeleteUserIncomeCategoryUseCase {

    override suspend operator fun invoke(categoryId: String): Resource<Unit> =
        categoryIncomeRepository.deleteUserIncomeCategory(categoryId)
}
