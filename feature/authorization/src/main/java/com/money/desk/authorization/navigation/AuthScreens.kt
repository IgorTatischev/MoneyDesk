package com.money.desk.authorization.navigation

internal sealed class AuthScreens(val route: String) {
    data object SignIn: AuthScreens(route = "sign_in_screen")
    data object SignUp: AuthScreens(route = "sign_up_screen")
    data object Forgot: AuthScreens(route = "forgot_screen")
}