package com.money.categories.navigation

import androidx.navigation.NavController
import com.money.common.DrawerScreen
import com.money.common.safeNavigate
import kotlinx.serialization.Serializable

@Serializable
object CategoriesGraph : DrawerScreen

fun NavController.navigateToCategoriesGraph() = safeNavigate { navigate(CategoriesGraph) }
