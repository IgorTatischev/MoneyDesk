package com.money.desk.authorization.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.desk.authorization.presentation.screens.forgot_screen.ForgotPasswordScreen
import com.money.desk.authorization.presentation.screens.sign_in.SignInScreen
import com.money.desk.authorization.presentation.screens.sign_up.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object AuthGraph

fun NavController.navigateToAuthGraph() {
    navigate(AuthGraph)
}

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<AuthGraph>(
        startDestination = AuthScreens.SignIn
    ){
        composable<AuthScreens.SignIn> {
            SignInScreen(
                onSignInClick = {
                    //navController.navigateUp()
                },
                onSignUpClick = {
                    navController.navigate(AuthScreens.SignUp)
                },
                onForgotClick = {
                    navController.navigate(AuthScreens.Forgot)
                }
            )
        }
        composable<AuthScreens.SignUp> {
            SignUpScreen()
        }
        composable<AuthScreens.Forgot> {
            ForgotPasswordScreen()
        }
    }
}

