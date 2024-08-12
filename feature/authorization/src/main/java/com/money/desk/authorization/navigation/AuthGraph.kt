package com.money.desk.authorization.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.money.desk.authorization.presentation.screens.forgot_screen.ForgotPasswordScreen
import com.money.desk.authorization.presentation.screens.sign_in.SignInScreen
import com.money.desk.authorization.presentation.screens.sign_up.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object AuthGraph

fun NavController.navigateToAuthGraph() {
    navigate(AuthGraph) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavController, navigateToMain: () -> Unit) {
    navigation<AuthGraph>(
        startDestination = AuthScreens.SignIn()
    ) {
        composable<AuthScreens.SignIn> {

            val loginFromRegister = it.toRoute<AuthScreens.SignIn>().login

            SignInScreen(
                navigateToMain = navigateToMain,
                navigateToSignUp = {
                    navController.navigate(AuthScreens.SignUp)
                },
                navigateForgot = {
                    navController.navigate(AuthScreens.Forgot)
                },
                loginFromRegister = loginFromRegister
            )
        }
        composable<AuthScreens.SignUp> {
            SignUpScreen(
                navigateBack = { navController.popBackStack() },
                navigateToSignIn = {
                    navController.navigate(AuthScreens.SignIn(it)) {
                        popUpTo<AuthScreens.SignIn> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AuthScreens.Forgot> {
            ForgotPasswordScreen()
        }
    }
}

