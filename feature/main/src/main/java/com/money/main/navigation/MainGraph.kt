package com.money.main.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.main.presentation.screens.main_screen.MainScreen

const val MAIN_GRAPH = "main"

fun NavController.navigateToMainGraph() {
    navigate(MAIN_GRAPH)
}

fun NavGraphBuilder.mainGraph(navController: NavController, drawerState: DrawerState) {

    navigation(
        startDestination = MainScreens.MainScreen.route,
        route = MAIN_GRAPH
    ) {
        composable(route = MainScreens.MainScreen.route) {
            MainScreen(drawerState = drawerState)
        }
    }
}