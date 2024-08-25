package com.money.categories.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.categories.presentation.screens.categories_screen.CategoryScreen
import com.money.common.safeNavigate
import kotlinx.serialization.Serializable

@Serializable
object CategoriesGraph

fun NavController.navigateToCategoriesGraph() = safeNavigate { navigate(CategoriesGraph) }

fun NavGraphBuilder.categoriesGraph(navController: NavController, drawerState: DrawerState) {

    navigation<CategoriesGraph>(
        startDestination = CategoriesScreens.CategoryScreen,
    ) {
        composable<CategoriesScreens.CategoryScreen> {
            CategoryScreen(drawerState = drawerState)
        }
    }
}