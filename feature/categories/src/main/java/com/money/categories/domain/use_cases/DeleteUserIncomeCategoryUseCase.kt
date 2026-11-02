package com.money.categories.domain.use_cases

import com.money.categories.domain.repository.CategoryIncomeRepository
import com.money.common.Resource
import com.money.common.usecases.BaseUseCase
import javax.inject.Inject

internal class DeleteUserIncomeCategoryUseCase @Inject constructor(
    private val categoryIncomeRepository: CategoryIncomeRepository,
) : BaseUseCase {

    suspend operator fun invoke(categoryId: String): Resource<Unit> =
        categoryIncomeRepository.deleteUserIncomeCategory(categoryId)
}
