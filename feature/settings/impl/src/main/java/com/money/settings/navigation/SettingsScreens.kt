package com.money.settings.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed class SettingsScreens {
    @Serializable
    data object SettingsScreen: SettingsScreens()
}