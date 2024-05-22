package com.money.desk.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.DonutLarge
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.money.categories.navigation.CATEGORIES_GRAPH
import com.money.main.navigation.MAIN_GRAPH
import com.money.settings.navigation.SETTINGS_GRAPH
import com.money.wallet.navigation.WALLET_GRAPH

enum class DrawerMenu(
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val title: String,
    val route: String,
) {
    //todo string res
    MAIN(Icons.Outlined.DonutLarge, Icons.Filled.DonutLarge, "Main", MAIN_GRAPH),
    CATEGORIES(Icons.Outlined.Category, Icons.Filled.Category, "Categories", CATEGORIES_GRAPH),
    SETTINGS(Icons.Outlined.Settings, Icons.Filled.Settings, "Settings", SETTINGS_GRAPH),
    WALLET(Icons.Outlined.Savings, Icons.Filled.Savings, "Wallet", WALLET_GRAPH)
}