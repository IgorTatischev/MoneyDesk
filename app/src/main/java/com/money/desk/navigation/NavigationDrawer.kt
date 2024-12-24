package com.money.desk.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.money.categories.navigation.categoriesGraph
import com.money.common.safeNavigate
import com.money.desk.R
import com.money.main.navigation.MainGraph
import com.money.main.navigation.mainGraph
import com.money.settings.navigation.settingsGraph
import com.money.wallet.navigation.walletGraph
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object DrawerRoute

fun NavController.navigateToDrawer() = safeNavigate { navigate(route = DrawerRoute) }

fun NavGraphBuilder.navigationDrawerHost(signOut: () -> Unit) {

    composable<DrawerRoute> {
        val navController = rememberNavController()
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column {
                        DrawerMenu.entries.forEach { item ->

                            val isSelected = currentDestination?.hierarchy?.any {
                                it.hasRoute(item.route::class)
                            } == true

                            NavigationDrawerItem(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                label = { Text(text = stringResource(id = item.title)) },
                                selected = isSelected,
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.safeNavigate {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )

                        }
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = signOut
                        ) {
                            Text(
                                text = stringResource(id = R.string.exit),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = MainGraph,
            ) {
                mainGraph(navController, drawerState)
                categoriesGraph(navController, drawerState)
                walletGraph(navController, drawerState)
                settingsGraph(navController, drawerState)
            }
        }
    }
}