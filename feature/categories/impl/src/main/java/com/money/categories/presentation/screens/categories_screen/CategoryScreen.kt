package com.money.categories.presentation.screens.categories_screen

import kotlinx.coroutines.flow.first
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.money.ui.theme.MoneyDeskTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.money.categories.domain.model.CategoryModel
import com.money.categories.presentation.components.AddCategoryBottomSheet
import com.money.categories.presentation.components.EmojiText
import com.money.ui.util.toComposeColorOrNull
import com.money.ui.components.DrawerTopAppBar
import com.money.ui.data.CategoryTabs
import com.money.ui.util.NoRippleInteractionSource
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
internal fun CategoryScreen(
    drawerState: DrawerState,
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val resources by rememberUpdatedState(LocalResources.current)
    val snackBarHostState = remember { SnackbarHostState() }

    val sheet = (state as? CategoryScreenState.Content)?.addCategorySheet
    // Last shown sheet state, kept while the sheet animates out after the VM closed it.
    var lastSheet by remember { mutableStateOf<AddCategorySheetState?>(null) }
    val sheetToShow = sheet ?: lastSheet
    val isSheetShown by rememberUpdatedState(sheetToShow != null)

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is CategoryScreenEffect.ShowSnackbar -> {
                    snapshotFlow { isSheetShown }.first { !it }
                    snackBarHostState.showSnackbar(
                        if (effect.messageRes != null)
                            resources.getString(effect.messageRes)
                        else effect.message.toString()
                    )
                }
            }
        }
    }

    SideEffect { if (sheet != null && sheet != lastSheet) lastSheet = sheet }

    CategoryScreenContent(
        state = state,
        drawerState = drawerState,
        snackbarHostState = snackBarHostState,
        onTabSelected = { viewModel.processEvent(CategoryScreenEvent.SelectTab(it)) },
        onAddCategoryClick = { viewModel.processEvent(CategoryScreenEvent.AddCategoryClick(it)) },
        onCategoryClick = { category, tab ->
            viewModel.processEvent(CategoryScreenEvent.CategoryClick(category, tab))
        },
    )

    sheetToShow?.let { shownSheet ->
        AddCategoryBottomSheet(
            state = shownSheet,
            visible = sheet != null,
            onDismiss = { viewModel.processEvent(CategoryScreenEvent.DismissAddCategory) },
            onHidden = { lastSheet = null },
            onNameChange = { viewModel.processEvent(CategoryScreenEvent.AddCategoryNameChange(it)) },
            onTypeChange = { viewModel.processEvent(CategoryScreenEvent.AddCategoryTypeChange(it)) },
            onColorChange = { viewModel.processEvent(CategoryScreenEvent.AddCategoryColorChange(it)) },
            onEmojiChange = { viewModel.processEvent(CategoryScreenEvent.AddCategoryEmojiChange(it)) },
            onSave = { viewModel.processEvent(CategoryScreenEvent.SaveCategory) },
            onDelete = { viewModel.processEvent(CategoryScreenEvent.DeleteCategory) },
        )
    }
}

@Composable
private fun CategoryScreenContent(
    state: CategoryScreenState,
    drawerState: DrawerState,
    snackbarHostState: SnackbarHostState,
    onTabSelected: (CategoryTabs) -> Unit,
    onAddCategoryClick: (CategoryTabs) -> Unit,
    onCategoryClick: (CategoryModel, CategoryTabs) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            DrawerTopAppBar(
                drawerState = drawerState,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is CategoryScreenState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                is CategoryScreenState.Error -> Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    text = state.message,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                )

                is CategoryScreenState.Content -> CategoryTabsContent(
                    state = state,
                    onTabSelected = onTabSelected,
                    onAddCategoryClick = onAddCategoryClick,
                    onCategoryClick = onCategoryClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryTabsContent(
    state: CategoryScreenState.Content,
    onTabSelected: (CategoryTabs) -> Unit,
    onAddCategoryClick: (CategoryTabs) -> Unit,
    onCategoryClick: (CategoryModel, CategoryTabs) -> Unit,
) {
    val tabs = CategoryTabs.entries
    val pagerState = rememberPagerState(initialPage = state.selectedTab.ordinal) { tabs.size }
    // Whether each page's content overflows; drives the shared FAB below.
    val scrollableByPage = remember { mutableStateMapOf<Int, Boolean>() }

    LaunchedEffect(state.selectedTab) {
        if (pagerState.currentPage != state.selectedTab.ordinal) {
            pagerState.animateScrollToPage(state.selectedTab.ordinal)
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { onTabSelected(tabs[it]) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                            MaterialTheme.colorScheme.tertiary,
                            FilterChipDefaults.shape,
                        )
                        .zIndex(1f)
                )
            },
            divider = {}
        ) {
            tabs.forEach { tab ->
                val selected = pagerState.currentPage == tab.ordinal
                Tab(
                    modifier = Modifier.zIndex(2f),
                    selected = selected,
                    interactionSource = NoRippleInteractionSource(),
                    onClick = { onTabSelected(tab) },
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                beyondViewportPageCount = tabs.size - 1,
            ) { page ->
                val tab = tabs[page]
                val categories = when (tab) {
                    CategoryTabs.Income -> state.incomeCategories
                    CategoryTabs.Expense -> state.expenseCategories
                }
                CategoriesPage(
                    categories = categories,
                    onAddCategoryClick = { onAddCategoryClick(tab) },
                    onCategoryClick = { onCategoryClick(it, tab) },
                    onScrollableChange = { scrollableByPage[page] = it },
                )
            }

            // One FAB for the whole pager (not one per page), so it doesn't move with the
            // page or replay its enter animation when switching tabs.
            androidx.compose.animation.AnimatedVisibility(
                visible = scrollableByPage[pagerState.currentPage] == true,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                FloatingActionButton(
                    onClick = { onAddCategoryClick(tabs[pagerState.currentPage]) },
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoriesPage(
    categories: List<CategoryModel>,
    onAddCategoryClick: () -> Unit,
    onCategoryClick: (CategoryModel) -> Unit,
    onScrollableChange: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    val isScrollable by remember { derivedStateOf { scrollState.maxValue > 0 } }
    val animatedCategories = rememberAnimatedItems(categories, key = { it.id.ifBlank { it.name } })

    LaunchedEffect(isScrollable) { onScrollableChange(isScrollable) }

    Box(modifier = Modifier.fillMaxSize()) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(10.dp)
                .padding(bottom = if (isScrollable) 80.dp else 0.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            animatedCategories.forEach { item ->
                key(item.key) {
                    AnimatedVisibility(
                        visibleState = item.visibleState,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut(),
                    ) {
                        CategoryItem(
                            category = item.value,
                            onClick = { onCategoryClick(item.value) },
                        )
                    }
                }
            }

            AddCategoryButton(
                modifier = Modifier.alpha(if (isScrollable) 0f else 1f),
                enabled = !isScrollable,
                onClick = onAddCategoryClick,
            )
        }
    }
}

private class AnimatedItem<T>(
    val key: Any,
    val value: T,
    val visibleState: MutableTransitionState<Boolean>,
)

/**
 * Keeps removed items around (with a hiding [MutableTransitionState]) until their exit
 * animation finishes; newly added items start hidden and animate in. Items present on
 * first composition are shown immediately, without animation.
 */
@Composable
private fun <T> rememberAnimatedItems(items: List<T>, key: (T) -> Any): List<AnimatedItem<T>> {
    // Plain holder, not state: it is only read to diff against the next items list.
    val previousHolder = remember { arrayOfNulls<List<AnimatedItem<T>>>(1) }
    val previous = previousHolder[0]
    val currentKeys = items.mapTo(HashSet(), key)
    val previousByKey = previous.orEmpty().associateBy { it.key }

    val result = items.mapTo(mutableListOf()) { item ->
        val itemKey = key(item)
        val state = previousByKey[itemKey]?.visibleState
            // Items of the very first composition appear without animation.
            ?: MutableTransitionState(previous == null).apply { targetState = true }
        AnimatedItem(itemKey, item, state)
    }

    val removed = mutableListOf<AnimatedItem<T>>()
    previous.orEmpty().forEachIndexed { oldIndex, old ->
        val finishedHiding = old.visibleState.isIdle && !old.visibleState.currentState
        if (old.key in currentKeys || finishedHiding) return@forEachIndexed
        // Keep it near its old position while it plays the exit animation.
        result.add(oldIndex.coerceAtMost(result.size), old)
        removed += old
    }

    SideEffect {
        previousHolder[0] = result
        removed.forEach { it.visibleState.targetState = false }
        // An item that was re-added while still hiding should come back.
        result.filterNot { it in removed }.forEach { it.visibleState.targetState = true }
    }
    return result
}

private val CategoryItemSize = 80.dp
private val CategoryLabelHeight = 18.dp

@Composable
private fun CategoryItem(
    category: CategoryModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.width(CategoryItemSize),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(CategoryLabelHeight),
            text = category.name,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        val containerColor = remember(category.color) {
            category.color.toComposeColorOrNull() ?: Color.Transparent
        }
        OutlinedIconButton(
            modifier = Modifier
                .clip(CircleShape)
                .size(CategoryItemSize),
            colors = IconButtonDefaults.outlinedIconButtonColors(containerColor = containerColor),
            onClick = onClick,
        ) {
            EmojiText(text = category.iconChar)
        }
    }
}

@Composable
private fun AddCategoryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier.width(CategoryItemSize),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(CategoryLabelHeight))
        FilledIconButton(
            modifier = Modifier
                .clip(CircleShape)
                .size(CategoryItemSize),
            enabled = enabled,
            onClick = onClick,
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
            )
        }
    }
}

private val PreviewIncomeCategories = listOf(
    CategoryModel(id = "1", name = "Salary", isDefault = true, color = "#66BB6A", iconChar = "💰"),
    CategoryModel(id = "2", name = "Freelance", color = "#42A5F5", iconChar = "💻"),
    CategoryModel(id = "3", name = "Gifts", color = "#EC407A", iconChar = "🎁"),
)

private val PreviewExpenseCategories = listOf(
    "🛒" to "Groceries", "🚌" to "Transport", "🍔" to "Restaurants", "🏠" to "Rent",
    "💊" to "Health", "🎬" to "Movies", "👕" to "Clothes", "📱" to "Phone",
    "✈️" to "Travel", "🐶" to "Pets", "📚" to "Books", "⚽" to "Sport",
    "🎮" to "Games", "☕" to "Coffee", "🧾" to "Bills", "🎓" to "Education",
    "🛠️" to "Repair", "🚗" to "Car", "🎉" to "Parties", "💇" to "Beauty",
    "🧸" to "Kids", "🏥" to "Insurance", "🍷" to "Wine", "🌱" to "Garden",
).mapIndexed { index, (emoji, name) ->
    CategoryModel(
        id = "e$index",
        name = name,
        isDefault = index < 4,
        color = listOf("#EF5350", "#FFA726", "#26A69A", "#5C6BC0", "#AB47BC")[index % 5],
        iconChar = emoji,
    )
}

@Composable
private fun CategoryScreenContentPreview(selectedTab: CategoryTabs) {
    MoneyDeskTheme {
        CategoryScreenContent(
            state = CategoryScreenState.Content(
                selectedTab = selectedTab,
                incomeCategories = PreviewIncomeCategories,
                expenseCategories = PreviewExpenseCategories,
            ),
            drawerState = rememberDrawerState(DrawerValue.Closed),
            snackbarHostState = remember { SnackbarHostState() },
            onTabSelected = {},
            onAddCategoryClick = {},
            onCategoryClick = { _, _ -> },
        )
    }
}

@Preview(name = "Income: few categories", showBackground = true)
@Composable
private fun CategoryScreenIncomePreview() = CategoryScreenContentPreview(CategoryTabs.Income)

@Preview(name = "Expense: scrollable, FAB", showBackground = true)
@Composable
private fun CategoryScreenExpensePreview() = CategoryScreenContentPreview(CategoryTabs.Expense)
