package com.money.settings.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.common.DrawerScreen
import com.money.common.safeNavigate
import com.money.settings.presentation.screens.settings_screen.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object SettingsGraph: DrawerScreen

fun NavController.navigateToSettingsGraph() = safeNavigate { navigate(route = SettingsGraph) }

fun NavGraphBuilder.settingsGraph(navController: NavController, drawerState: DrawerState) {

    navigation<SettingsGraph>(
        startDestination = SettingsScreens.SettingsScreen
    ) {
        composable<SettingsScreens.SettingsScreen> {
            SettingsScreen(drawerState = drawerState)
        }
    }
}