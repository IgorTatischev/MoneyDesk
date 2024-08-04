package com.money.desk.navigation

import androidx.annotation.StringRes
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
import com.money.categories.navigation.CategoriesGraph
import com.money.desk.R
import com.money.main.navigation.MainGraph
import com.money.settings.navigation.SettingsGraph
import com.money.wallet.navigation.WalletGraph

enum class DrawerMenu(
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val title: Int,
    val route: Any,
) {
    MAIN(Icons.Outlined.DonutLarge, Icons.Filled.DonutLarge, R.string.title_main, MainGraph),
    CATEGORIES(Icons.Outlined.Category, Icons.Filled.Category, R.string.title_categories, CategoriesGraph),
    WALLET(Icons.Outlined.Savings, Icons.Filled.Savings, R.string.title_wallet, WalletGraph),
    SETTINGS(Icons.Outlined.Settings, Icons.Filled.Settings, R.string.title_settings, SettingsGraph),
}