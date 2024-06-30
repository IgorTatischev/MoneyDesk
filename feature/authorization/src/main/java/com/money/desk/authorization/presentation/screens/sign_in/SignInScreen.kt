package com.money.desk.authorization.presentation.screens.sign_in

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SignInScreen(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    Text(text = "SignIn")
}