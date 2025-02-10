package com.money.categories.presentation.screens.categories_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.money.ui.components.DrawerTopAppBar
import com.money.ui.data.CategoryTabs
import com.money.ui.util.NoRippleInteractionSource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(drawerState: DrawerState) {

    val scope = rememberCoroutineScope()
    val pages = CategoryTabs.entries
    val pagerState = rememberPagerState { CategoryTabs.entries.size }
    //val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    //var selectedTabIndex by remember { mutableIntStateOf(0) }
    //LaunchedEffect(selectedTabIndex) {
    //    pagerState.animateScrollToPage(selectedTabIndex)
    //}

    Scaffold(
        topBar = {
            DrawerTopAppBar(
                drawerState = drawerState,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                indicator = {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(pagerState.currentPage)
                            .fillMaxSize()
                            .padding(10.dp)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                FilterChipDefaults.shape,
                            )
                            .zIndex(1f)
                    )
                },
                divider = {}
            ) {
                pages.forEachIndexed { index, tab ->
                    val selected = pagerState.currentPage == index
                    Tab(
                        modifier = Modifier.zIndex(2f),
                        selected = selected,
                        interactionSource = NoRippleInteractionSource(),
                        onClick = {
                            //todo to ui-effect and launch
                            scope.launch {
                                pagerState.animateScrollToPage(tab.ordinal)
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(id = tab.textId),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selected) MaterialTheme.colorScheme.onSecondary else Color.Unspecified
                            )
                        },
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    //todo
                    when (pagerState.currentPage) {
                        0 -> {
                            Text(text = stringResource(id = pages[pagerState.currentPage].textId))
                        }

                        1 -> {
                            Text(text = stringResource(id = pages[pagerState.currentPage].textId))
                        }
                    }
                }
            }
        }
    }
}