package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.categories.data.repository.CategoryIncomeRepository
import com.money.common.Resource
import javax.inject.Inject

internal class AddUserIncomeCategoryUseCaseImpl @Inject constructor(
    private val categoryIncomeRepository: CategoryIncomeRepository,
) : AddUserIncomeCategoryUseCase {

    override suspend operator fun invoke(category: CategoryModel): Resource<Unit> =
        categoryIncomeRepository.addUserIncomeCategory(category)
}
