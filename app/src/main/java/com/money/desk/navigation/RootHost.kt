package com.money.desk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.money.desk.authorization.navigation.AuthGraph
import com.money.desk.authorization.navigation.authNavGraph
import com.money.desk.authorization.navigation.navigateToAuthGraph

@Composable
fun RootHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        //startDestination = DrawerRoute //todo if not login go to auth
        startDestination = AuthGraph
    ) {
        navigationDrawerHost(navigateToAuth = { navController.navigateToAuthGraph() })
        authNavGraph(
            navController = navController,
            navigateToMain = { navController.navigateToDrawer() }
        )
    }
}