package com.money.settings.navigation

internal sealed class SettingsScreens(
    val route: String,
) {
    data object SettingsScreen: SettingsScreens(route = "settings_screen")
}