package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.categories.data.repository.CategoryIncomeRepository
import com.money.common.Resource
import javax.inject.Inject

internal class UpdateUserIncomeCategoryUseCaseImpl @Inject constructor(
    private val categoryIncomeRepository: CategoryIncomeRepository,
) : UpdateUserIncomeCategoryUseCase {

    override suspend operator fun invoke(categoryId: String, category: CategoryModel): Resource<Unit> =
        categoryIncomeRepository.updateUserIncomeCategory(categoryId, category)
}
