package com.money.desk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.money.desk.authorization.navigation.AuthGraph
import com.money.desk.authorization.navigation.authNavGraph
import com.money.desk.authorization.navigation.navigateToAuthGraph

@Composable
fun RootHost(isLogin: Boolean, onSignOut: () -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isLogin) DrawerRoute else AuthGraph
    ) {
        navigationDrawerHost(signOut = {
            onSignOut()
            navController.navigateToAuthGraph()
        })
        authNavGraph(
            navController = navController,
            navigateToMain = { navController.navigateToDrawer() }
        )
    }
}