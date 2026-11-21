package com.money.categories.presentation.screens.categories_screen

import androidx.lifecycle.viewModelScope
import com.money.categories.domain.model.CategoryModel
import com.money.categories.impl.R
import com.money.categories.domain.use_cases.AddUserExpenseCategoryUseCase
import com.money.categories.domain.use_cases.AddUserIncomeCategoryUseCase
import com.money.categories.domain.use_cases.DeleteUserExpenseCategoryUseCase
import com.money.categories.domain.use_cases.DeleteUserIncomeCategoryUseCase
import com.money.categories.domain.use_cases.GetExpenseCategoriesUseCase
import com.money.categories.domain.use_cases.GetIncomeCategoriesUseCase
import com.money.categories.domain.use_cases.UpdateUserExpenseCategoryUseCase
import com.money.categories.domain.use_cases.UpdateUserIncomeCategoryUseCase
import com.money.categories.presentation.components.DefaultCategoryColor
import com.money.common.BaseViewModel
import com.money.common.Resource
import com.money.ui.data.CategoryTabs
import com.money.ui.util.toComposeColorOrNull
import com.money.ui.util.toHexColorString
import androidx.compose.ui.graphics.toArgb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CategoryViewModel @Inject constructor(
    private val addUserIncomeCategoryUseCase: AddUserIncomeCategoryUseCase,
    private val addUserExpenseCategoryUseCase: AddUserExpenseCategoryUseCase,
    private val updateUserIncomeCategoryUseCase: UpdateUserIncomeCategoryUseCase,
    private val updateUserExpenseCategoryUseCase: UpdateUserExpenseCategoryUseCase,
    private val deleteUserIncomeCategoryUseCase: DeleteUserIncomeCategoryUseCase,
    private val deleteUserExpenseCategoryUseCase: DeleteUserExpenseCategoryUseCase,
    getIncomeCategoriesUseCase: GetIncomeCategoriesUseCase,
    getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
) : BaseViewModel<CategoryScreenState, CategoryScreenEvent, CategoryScreenEffect>(
    initialState = CategoryScreenState.Loading
) {

    private val selectedTab = MutableStateFlow(CategoryTabs.Income)
    private val addSheet = MutableStateFlow<AddCategorySheetState?>(null)

    private val resultFlow: StateFlow<CategoryScreenState> = combine(
        selectedTab,
        addSheet,
        getIncomeCategoriesUseCase(),
        getExpenseCategoriesUseCase(),
    ) { tab, sheet, income, expense ->
        when {
            income is Resource.Error -> CategoryScreenState.Error(income.message.orEmpty())
            expense is Resource.Error -> CategoryScreenState.Error(expense.message.orEmpty())
            income is Resource.Success && expense is Resource.Success ->
                CategoryScreenState.Content(
                    selectedTab = tab,
                    incomeCategories = income.data.orEmpty(),
                    expenseCategories = expense.data.orEmpty(),
                    addCategorySheet = sheet,
                )

            else -> CategoryScreenState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = CategoryScreenState.Loading,
    )

    init {
        resultFlow
            .onEach { newState -> updateState { newState } }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: CategoryScreenEvent) {
        when (event) {
            is CategoryScreenEvent.SelectTab -> selectedTab.value = event.tab

            is CategoryScreenEvent.AddCategoryClick ->
                addSheet.value = AddCategorySheetState(type = event.tab)

            is CategoryScreenEvent.DismissAddCategory ->
                addSheet.update { if (it?.isBusy == true) it else null }

            is CategoryScreenEvent.AddCategoryNameChange ->
                addSheet.update { it?.copy(name = event.name) }

            is CategoryScreenEvent.AddCategoryTypeChange ->
                addSheet.update { it?.copy(type = event.type) }

            is CategoryScreenEvent.AddCategoryColorChange ->
                addSheet.update { it?.copy(color = event.color) }

            is CategoryScreenEvent.AddCategoryEmojiChange ->
                addSheet.update { it?.copy(emoji = event.emoji) }

            is CategoryScreenEvent.SaveCategory -> saveCategory()

            is CategoryScreenEvent.CategoryClick -> openEditSheet(event.category, event.tab)

            is CategoryScreenEvent.DeleteCategory -> deleteCategory()
        }
    }

    private fun openEditSheet(category: CategoryModel, tab: CategoryTabs) {
        // Default categories are shared by all users and can't be edited or deleted.
        if (category.isDefault || category.id.isBlank()) return
        addSheet.value = AddCategorySheetState(
            type = tab,
            name = category.name,
            color = category.color.toComposeColorOrNull()?.toArgb() ?: DefaultCategoryColor,
            emoji = category.iconChar,
            editingCategoryId = category.id,
        )
    }

    private fun deleteCategory() {
        val sheet = addSheet.value?.takeIf { !it.isBusy } ?: return
        val categoryId = sheet.editingCategoryId ?: return
        addSheet.value = sheet.copy(isDeleting = true)

        viewModelScope.launch {
            val result = when (sheet.type) {
                CategoryTabs.Income -> deleteUserIncomeCategoryUseCase(categoryId)
                CategoryTabs.Expense -> deleteUserExpenseCategoryUseCase(categoryId)
            }
            addSheet.value = null
            sendEffect(
                CategoryScreenEffect.ShowSnackbar(
                    if (result is Resource.Success) R.string.category_deleted
                    else R.string.category_delete_failed
                )
            )
        }
    }

    private fun saveCategory() {
        val sheet = addSheet.value?.takeIf { it.canSave } ?: return
        addSheet.value = sheet.copy(isSaving = true)

        viewModelScope.launch {
            val category = CategoryModel(
                name = sheet.name.trim(),
                isDefault = false,
                color = sheet.color.toHexColorString(),
                iconChar = sheet.emoji,
            )
            val editingId = sheet.editingCategoryId
            val result = when {
                editingId != null -> when (sheet.type) {
                    CategoryTabs.Income -> updateUserIncomeCategoryUseCase(editingId, category)
                    CategoryTabs.Expense -> updateUserExpenseCategoryUseCase(editingId, category)
                }

                sheet.type == CategoryTabs.Income -> addUserIncomeCategoryUseCase(category)
                else -> addUserExpenseCategoryUseCase(category)
            }
            addSheet.value = null
            val message = when {
                result !is Resource.Success -> R.string.category_save_failed
                editingId != null -> R.string.category_updated
                else -> R.string.category_added
            }
            if (result is Resource.Success) selectedTab.value = sheet.type
            sendEffect(CategoryScreenEffect.ShowSnackbar(message))
        }
    }
}
