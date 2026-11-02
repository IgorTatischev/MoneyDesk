package com.money.categories.domain.use_cases

import com.money.categories.domain.model.CategoryModel
import com.money.categories.domain.repository.CategoryIncomeRepository
import com.money.common.Resource
import com.money.common.usecases.BaseUseCase
import javax.inject.Inject

internal class UpdateUserIncomeCategoryUseCase @Inject constructor(
    private val categoryIncomeRepository: CategoryIncomeRepository,
) : BaseUseCase {

    suspend operator fun invoke(categoryId: String, category: CategoryModel): Resource<Unit> =
        categoryIncomeRepository.updateUserIncomeCategory(categoryId, category)
}
