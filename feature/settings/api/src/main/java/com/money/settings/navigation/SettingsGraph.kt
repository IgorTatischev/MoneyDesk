package com.money.settings.navigation

import androidx.navigation.NavController
import com.money.common.DrawerScreen
import com.money.common.safeNavigate
import kotlinx.serialization.Serializable

@Serializable
object SettingsGraph : DrawerScreen

fun NavController.navigateToSettingsGraph() = safeNavigate { navigate(SettingsGraph) }
