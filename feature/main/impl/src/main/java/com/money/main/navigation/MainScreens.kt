package com.money.main.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed class MainScreens {
    @Serializable
    data object MainScreen: MainScreens()
    @Serializable
    data object MainSecondScreen: MainScreens()
}