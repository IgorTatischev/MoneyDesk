package com.money.desk.authorization.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed class AuthScreens {
    @Serializable
    data object SignIn: AuthScreens()
    @Serializable
    data object SignUp: AuthScreens()
    @Serializable
    data object Forgot: AuthScreens()
}