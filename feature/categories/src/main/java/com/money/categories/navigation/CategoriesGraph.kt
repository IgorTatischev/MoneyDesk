package com.money.categories.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.categories.presentation.screens.categories_screen.CategoryScreen

const val CATEGORIES_GRAPH = "categories"

fun NavController.navigateToCategoriesGraph() {
    navigate(CATEGORIES_GRAPH)
}

fun NavGraphBuilder.categoriesGraph(navController: NavController, drawerState: DrawerState) {

    navigation(
        startDestination = CategoriesScreens.CategoryScreen.route,
        route = CATEGORIES_GRAPH
    ) {
        composable(route = CategoriesScreens.CategoryScreen.route) {
            CategoryScreen(drawerState = drawerState)
        }
    }
}