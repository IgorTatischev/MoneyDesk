package com.money.desk.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.money.desk.authorization.navigation.AuthGraph
import com.money.desk.authorization.navigation.authNavGraph
import com.money.desk.authorization.navigation.navigateToAuthGraph

@Composable
fun RootHost(isLogin: Boolean, onboardingCheck: Boolean, onSignOut: () -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isLogin) DrawerRoute else AuthGraph,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(700)
            )
        }
    ) {
        navigationDrawerHost(
            signOut = {
                onSignOut()
                navController.navigateToAuthGraph()
            }
        )
        authNavGraph(
            navController = navController,
            navigateToMain = { navController.navigateToDrawer() },
            onboardingCheck = onboardingCheck
        )
    }
}