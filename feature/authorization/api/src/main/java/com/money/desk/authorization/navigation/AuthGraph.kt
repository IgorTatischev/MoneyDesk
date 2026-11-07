package com.money.desk.authorization.navigation

import androidx.navigation.NavController
import com.money.common.safeNavigate
import kotlinx.serialization.Serializable

@Serializable
object AuthGraph

fun NavController.navigateToAuthGraph() = safeNavigate {
    navigate(AuthGraph) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
