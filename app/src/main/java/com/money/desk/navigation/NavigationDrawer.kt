package com.money.desk.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.money.categories.navigation.categoriesGraph
import com.money.main.navigation.MAIN_GRAPH
import com.money.main.navigation.mainGraph
import com.money.settings.navigation.settingsGraph
import com.money.wallet.navigation.walletGraph
import kotlinx.coroutines.launch

const val NAVIGATION_DRAWER_ROUTE = "drawer"

fun NavGraphBuilder.navigationDrawerHost() {

    composable(route = NAVIGATION_DRAWER_ROUTE) {

        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by rememberSaveable { mutableIntStateOf(0) }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column {
                        DrawerMenu.entries.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                label = { Text(text = item.title) },
                                selected = index == selectedItem,
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedItem) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    selectedItem = index
                                    navController.navigate(item.route)
                                }
                            )
                        }
                    }

                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = MAIN_GRAPH,
            ) {
                mainGraph(navController, drawerState)
                categoriesGraph(navController, drawerState)
                walletGraph(navController, drawerState)
                settingsGraph(navController, drawerState)
            }
        }
    }
}



