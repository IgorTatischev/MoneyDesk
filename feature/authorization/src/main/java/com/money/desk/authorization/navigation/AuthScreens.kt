package com.money.desk.authorization.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed class AuthScreens {
    @Serializable
    data class SignIn(val login: String = ""): AuthScreens()
    @Serializable
    data object SignUp: AuthScreens()
    @Serializable
    data object Forgot: AuthScreens()
}