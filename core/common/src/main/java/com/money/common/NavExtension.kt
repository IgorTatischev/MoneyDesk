package com.money.common

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

/**Type for NavDrawer routes**/
interface DrawerScreen

/**
 Process navigation if current lifecycle is RESUMED
 **/

fun NavController.safeNavigate(
    navigate: ()-> Unit
) {
    if (currentBackStackEntry?.lifecycleIsResumed() == true)
        navigate()
}

fun NavController.safeNavigateBack() {
    if (currentBackStackEntry?.lifecycleIsResumed() == true)
        popBackStack()
}

fun NavController.safeNavigateBack(
    route: String,
    inclusive: Boolean = false,
    saveState: Boolean = false,
) {
    if (currentBackStackEntry?.lifecycleIsResumed() == true)
        popBackStack(route, inclusive, saveState)
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED