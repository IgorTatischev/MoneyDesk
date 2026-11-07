package com.money.desk.authorization.presentation.screens.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.money.desk.authorization.presentation.screens.onboarding.components.OnboardingScreenPageContent
import com.money.desk.authorization.presentation.screens.onboarding.components.PageIndicator
import com.money.ui.components.MainButton
import com.money.ui.components.TopBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlin.enums.EnumEntries
import com.money.ui.R as CommonR

@Composable
internal fun OnboardingScreen(
    navigateToSignIn: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val pages = OnboardingPages.entries
    val pagerState = rememberPagerState { pages.size }

    LaunchedEffect(true) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is OnboardingScreenEffect.NavigateToAuthScreen -> navigateToSignIn()
            }
        }
    }

    LaunchedEffect(state.currentPage) {
        if (state.currentPage != pages[pagerState.currentPage]) {
            pagerState.animateScrollToPage(pages.indexOf(state.currentPage))
        }
    }

    OnboardingScreenContent(
        pagerState = pagerState,
        pages = pages,
        onBackClick = {
            viewModel.processEvent(OnboardingScreenEvent.OnBackClick(pages[pagerState.currentPage]))
        },
        onNextClick = {
            viewModel.processEvent(OnboardingScreenEvent.ChangePage(pages[it]))
        },
        onNavigateToAuth = {
            viewModel.processEvent(OnboardingScreenEvent.NavigateToAuth)
        },
    )
}

@Composable
private fun OnboardingScreenContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pages: EnumEntries<OnboardingPages>,
    onNavigateToAuth: () -> Unit,
    onNextClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {

    var backHandlerEnabled by remember { mutableStateOf(false) }
    var hasReachedLastPage by remember { mutableStateOf(pagerState.currentPage == pages.lastIndex) }
    var footerHeight by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .onEach {
                backHandlerEnabled = it != 0
                hasReachedLastPage = it == pages.lastIndex
            }
            .distinctUntilChanged()
            .collect { onNextClick(it) }
    }

    BackHandler(backHandlerEnabled) {
        onBackClick()
    }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                onBackClick = if (pagerState.currentPage > 0) onBackClick else null,
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
            ) { pageIndex ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    OnboardingScreenPageContent(
                        //modifier = Modifier.padding(bottom = footerHeight.pxToDp(LocalDensity.current)),
                        page = pages[pageIndex]
                    )
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .onGloballyPositioned {
                        footerHeight = it.size.height
                    }
            ) {
                PageIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    pagerState = pagerState,
                    animationDurationInMillis = integerResource(id = android.R.integer.config_shortAnimTime),
                )
                Spacer(modifier = Modifier.height(30.dp))

                if (!hasReachedLastPage) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 30.dp),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            modifier = Modifier.weight(0.6f),
                            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 0.dp),
                            onClick = onNavigateToAuth,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = stringResource(CommonR.string.skip),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                letterSpacing = 0.5.sp
                            )
                        }
                        MainButton(
                            modifier = Modifier.weight(1f),
                            onClick = { onNextClick(pagerState.currentPage + 1) },
                            text = stringResource(id = CommonR.string.next_page)
                        )
                    }
                } else {
                    MainButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 30.dp),
                        onClick = onNavigateToAuth,
                        text = stringResource(id = CommonR.string.start)
                    )
                }
            }
        }
    }
}

//fun Int.pxToDp(density: Density) = with(density) { this@pxToDp.toDp() }
