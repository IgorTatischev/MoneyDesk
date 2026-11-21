package com.money.categories.presentation.screens.categories_screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.money.categories.domain.model.CategoryModel
import com.money.categories.presentation.components.DefaultCategoryColor
import com.money.categories.presentation.components.DefaultCategoryEmoji
import com.money.ui.data.CategoryTabs

@Immutable
internal sealed class CategoryScreenState {
    data object Loading : CategoryScreenState()

    data class Error(val message: String) : CategoryScreenState()

    data class Content(
        val selectedTab: CategoryTabs = CategoryTabs.Income,
        val incomeCategories: List<CategoryModel> = emptyList(),
        val expenseCategories: List<CategoryModel> = emptyList(),
        /** Non-null while the "add category" bottom sheet is shown. */
        val addCategorySheet: AddCategorySheetState? = null,
    ) : CategoryScreenState()
}

@Immutable
internal data class AddCategorySheetState(
    val type: CategoryTabs,
    val name: String = "",
    val color: Int = DefaultCategoryColor,
    val emoji: String = DefaultCategoryEmoji,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    /** Firestore id of the category being edited; null when adding a new one. */
    val editingCategoryId: String? = null,
) {
    val isEditMode: Boolean get() = editingCategoryId != null
    val isBusy: Boolean get() = isSaving || isDeleting
    val canSave: Boolean get() = name.isNotBlank() && !isBusy
}

internal sealed class CategoryScreenEvent {
    data class SelectTab(val tab: CategoryTabs) : CategoryScreenEvent()
    data class AddCategoryClick(val tab: CategoryTabs) : CategoryScreenEvent()
    data object DismissAddCategory : CategoryScreenEvent()
    data class AddCategoryNameChange(val name: String) : CategoryScreenEvent()
    data class AddCategoryTypeChange(val type: CategoryTabs) : CategoryScreenEvent()
    data class AddCategoryColorChange(val color: Int) : CategoryScreenEvent()
    data class AddCategoryEmojiChange(val emoji: String) : CategoryScreenEvent()
    data object SaveCategory : CategoryScreenEvent()
    data class CategoryClick(val category: CategoryModel, val tab: CategoryTabs) : CategoryScreenEvent()
    data object DeleteCategory : CategoryScreenEvent()
}

internal sealed class CategoryScreenEffect {
    data class ShowSnackbar(@StringRes val messageRes: Int? = null, val message: String? = null) : CategoryScreenEffect()
}
