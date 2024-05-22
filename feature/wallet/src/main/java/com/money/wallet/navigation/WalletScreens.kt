package com.money.wallet.navigation

internal sealed class WalletScreens(
    val route: String,
) {
    data object WalletScreen: WalletScreens(route = "wallet_screen")
}