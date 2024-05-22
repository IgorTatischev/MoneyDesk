package com.money.wallet.navigation

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.money.wallet.presentation.screens.wallet_screen.WalletScreen

const val WALLET_GRAPH = "wallet"

fun NavController.navigateToWalletGraph() {
    navigate(WALLET_GRAPH)
}

fun NavGraphBuilder.walletGraph(navController: NavController, drawerState: DrawerState) {

    navigation(
        startDestination = WalletScreens.WalletScreen.route,
        route = WALLET_GRAPH
    ) {
        composable(route = WalletScreens.WalletScreen.route) {
            WalletScreen(drawerState = drawerState)
        }
    }
}


