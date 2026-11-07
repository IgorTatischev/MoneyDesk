package com.money.desk.authorization.presentation.screens.sign_up

import androidx.annotation.StringRes

internal data class SignUpState(
    val loginText: String = "",
    val passwordText: String = "",
    val nameText: String = "",
    val isLoading: Boolean = false,
)

internal sealed class UiEffect {
    data class NavigateToSignInScreen(val login: String): UiEffect()
    data class ShowSnackbar(@StringRes val resId: Int? = null, val message: String? = null): UiEffect()
}