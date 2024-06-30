package com.money.wallet.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed class WalletScreens {
    @Serializable
    data object WalletScreen: WalletScreens()
}