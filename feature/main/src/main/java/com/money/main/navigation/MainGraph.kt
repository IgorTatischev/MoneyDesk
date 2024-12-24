package com.money.main.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.common.DrawerScreen
import com.money.common.safeNavigate
import com.money.common.safeNavigateBack
import com.money.main.presentation.screens.main_screen.MainScreen
import com.money.main.presentation.screens.main_screen.MainSecondScreen
import kotlinx.serialization.Serializable

@Serializable
object MainGraph: DrawerScreen

fun NavController.navigateToMainGraph() = safeNavigate { navigate(MainGraph) }

fun NavGraphBuilder.mainGraph(navController: NavController, drawerState: DrawerState) {

    navigation<MainGraph>(
        startDestination = MainScreens.MainScreen,
    ) {
        composable<MainScreens.MainScreen> {
            MainScreen(drawerState = drawerState) {
                navController.safeNavigate { navController.navigate(MainScreens.MainSecondScreen) }
            }
        }
        composable<MainScreens.MainSecondScreen> {
            MainSecondScreen {
                navController.safeNavigateBack()
            }
        }
    }
}