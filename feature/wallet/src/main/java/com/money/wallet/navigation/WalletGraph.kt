package com.money.wallet.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.common.DrawerScreen
import com.money.common.safeNavigate
import com.money.wallet.presentation.screens.wallet_screen.WalletScreen
import kotlinx.serialization.Serializable

@Serializable
object WalletGraph: DrawerScreen

fun NavController.navigateToWalletGraph() = safeNavigate { navigate(route = WalletGraph) }

fun NavGraphBuilder.walletGraph(navController: NavController, drawerState: DrawerState) {

    navigation<WalletGraph>(
        startDestination = WalletScreens.WalletScreen,
    ) {
        composable<WalletScreens.WalletScreen> {
            WalletScreen(drawerState = drawerState)
        }
    }
}