package com.money.main.navigation

import androidx.navigation.NavController
import com.money.common.DrawerScreen
import com.money.common.safeNavigate
import kotlinx.serialization.Serializable

@Serializable
object MainGraph : DrawerScreen

fun NavController.navigateToMainGraph() = safeNavigate { navigate(MainGraph) }
