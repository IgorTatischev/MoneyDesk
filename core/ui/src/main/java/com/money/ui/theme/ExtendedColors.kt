package com.money.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Семантические цвета, которых нет в M3 ColorScheme: доходы, расходы и состояние валидации.
 * Роли устроены как у primary/container: `income` на `onIncome`, `incomeContainer` на `onIncomeContainer`.
 *
 * Использование: `MaterialTheme.extendedColors.income`.
 */
@Immutable
data class ExtendedColors(
    val income: Color,
    val onIncome: Color,
    val incomeContainer: Color,
    val onIncomeContainer: Color,
    val expense: Color,
    val onExpense: Color,
    val expenseContainer: Color,
    val onExpenseContainer: Color,
    val validationSuccess: Color,
    val validationError: Color,
)

val LightExtendedColors = ExtendedColors(
    income = incomeLight,
    onIncome = onIncomeLight,
    incomeContainer = incomeContainerLight,
    onIncomeContainer = onIncomeContainerLight,
    expense = expenseLight,
    onExpense = onExpenseLight,
    expenseContainer = expenseContainerLight,
    onExpenseContainer = onExpenseContainerLight,
    validationSuccess = validationSuccessLight,
    validationError = validationErrorLight,
)

val DarkExtendedColors = ExtendedColors(
    income = incomeDark,
    onIncome = onIncomeDark,
    incomeContainer = incomeContainerDark,
    onIncomeContainer = onIncomeContainerDark,
    expense = expenseDark,
    onExpense = onExpenseDark,
    expenseContainer = expenseContainerDark,
    onExpenseContainer = onExpenseContainerDark,
    validationSuccess = validationSuccessDark,
    validationError = validationErrorDark,
)

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }

val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendedColors.current
