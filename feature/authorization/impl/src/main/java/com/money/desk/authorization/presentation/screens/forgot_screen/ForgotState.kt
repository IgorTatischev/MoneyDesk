package com.money.desk.authorization.presentation.screens.forgot_screen

internal sealed class UiEffect {
    data object NavigateToSignInScreen: UiEffect()
    data class ShowSnackbar(val message: String?): UiEffect()
}

internal data class ForgotState(
    val isLoading: Boolean = false,
    val emailText: String = "",
)