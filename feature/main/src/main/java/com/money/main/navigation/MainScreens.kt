package com.money.main.navigation

internal sealed class MainScreens(
    val route: String,
) {
    data object MainScreen: MainScreens(route = "main_screen")
}