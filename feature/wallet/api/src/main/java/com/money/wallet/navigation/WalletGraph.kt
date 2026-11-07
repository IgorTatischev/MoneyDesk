package com.money.wallet.navigation

import androidx.navigation.NavController
import com.money.common.DrawerScreen
import com.money.common.safeNavigate
import kotlinx.serialization.Serializable

@Serializable
object WalletGraph : DrawerScreen

fun NavController.navigateToWalletGraph() = safeNavigate { navigate(WalletGraph) }
