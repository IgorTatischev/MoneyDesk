package com.money.settings.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.settings.presentation.screens.settings_screen.SettingsScreen

const val SETTINGS_GRAPH = "settings"

fun NavController.navigateToSettingsGraph() {
    navigate(SETTINGS_GRAPH)
}

fun NavGraphBuilder.settingsGraph(navController: NavController, drawerState: DrawerState) {

    navigation(
        startDestination = SettingsScreens.SettingsScreen.route,
        route = SETTINGS_GRAPH
    ) {
        composable(route = SettingsScreens.SettingsScreen.route) {
            SettingsScreen(drawerState = drawerState)
        }
    }
}