package com.money.desk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.money.desk.authorization.navigation.AUTH_GRAPH
import com.money.desk.authorization.navigation.authNavGraph

@Composable
fun RootHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        //startDestination = NAVIGATION_DRAWER_ROUTE //todo if not login go to auth
        startDestination = AUTH_GRAPH
    ) {
        navigationDrawerHost()
        authNavGraph(navController)
    }
}