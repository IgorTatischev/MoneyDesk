package com.money.desk.authorization.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.desk.authorization.presentation.screens.forgot_screen.ForgotPasswordScreen
import com.money.desk.authorization.presentation.screens.sign_in.SignInScreen
import com.money.desk.authorization.presentation.screens.sign_up.SignUpScreen

const val AUTH_GRAPH = "authorization"

fun NavController.navigateToAuthGraph() {
    navigate(AUTH_GRAPH)
}

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = AUTH_GRAPH,
        startDestination = AuthScreens.SignIn.route
    ){
        composable(route = AuthScreens.SignIn.route) {
            SignInScreen(
                onSignInClick = {
                    //navController.navigateUp()
                },
                onSignUpClick = {
                    navController.navigate(AuthScreens.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreens.Forgot.route)
                }
            )
        }
        composable(route = AuthScreens.SignUp.route) {
            SignUpScreen()
        }
        composable(route = AuthScreens.Forgot.route) {
            ForgotPasswordScreen()
        }
    }
}

