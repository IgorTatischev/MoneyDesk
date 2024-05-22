package com.money.categories.navigation

internal sealed class CategoriesScreens(
    val route: String,
) {
    data object CategoryScreen: CategoriesScreens(route = "category_screen")
}