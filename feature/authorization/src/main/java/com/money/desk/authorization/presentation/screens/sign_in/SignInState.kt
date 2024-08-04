package com.money.desk.authorization.presentation.screens.sign_in

internal sealed class UiEffect {
    data object NavigateToMainScreen: UiEffect()
    data class ShowSnackbar(val message: String?): UiEffect()
}

internal data class SignInState(
    val isLoading: Boolean = false
)