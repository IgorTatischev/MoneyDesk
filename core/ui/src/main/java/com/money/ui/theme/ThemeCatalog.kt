package com.money.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.money.ui.util.ThemePreviews

/**
 * Каталог темы: палитра, типографика, формы, отступы. Не используется в приложении,
 * нужен как превью-«паспорт» дизайн-системы (светлая и тёмная версии).
 */
@Composable
private fun ThemeCatalog() {
    val scheme = MaterialTheme.colorScheme
    val ext = MaterialTheme.extendedColors

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(Spacing.m),
        verticalArrangement = Arrangement.spacedBy(Spacing.l)
    ) {
        Section("Color scheme") {
            ColorRow(
                "primary" to (scheme.primary to scheme.onPrimary),
                "primaryContainer" to (scheme.primaryContainer to scheme.onPrimaryContainer),
                "secondary" to (scheme.secondary to scheme.onSecondary),
                "secondaryContainer" to (scheme.secondaryContainer to scheme.onSecondaryContainer),
                "tertiary" to (scheme.tertiary to scheme.onTertiary),
                "tertiaryContainer" to (scheme.tertiaryContainer to scheme.onTertiaryContainer),
                "error" to (scheme.error to scheme.onError),
                "errorContainer" to (scheme.errorContainer to scheme.onErrorContainer),
            )
        }
        Section("Surfaces") {
            ColorRow(
                "surface" to (scheme.surface to scheme.onSurface),
                "surfaceDim" to (scheme.surfaceDim to scheme.onSurface),
                "surfaceBright" to (scheme.surfaceBright to scheme.onSurface),
                "containerLowest" to (scheme.surfaceContainerLowest to scheme.onSurface),
                "containerLow" to (scheme.surfaceContainerLow to scheme.onSurface),
                "container" to (scheme.surfaceContainer to scheme.onSurface),
                "containerHigh" to (scheme.surfaceContainerHigh to scheme.onSurface),
                "containerHighest" to (scheme.surfaceContainerHighest to scheme.onSurface),
                "surfaceVariant" to (scheme.surfaceVariant to scheme.onSurfaceVariant),
                "inverseSurface" to (scheme.inverseSurface to scheme.inverseOnSurface),
            )
        }
        Section("Extended: income / expense") {
            ColorRow(
                "income" to (ext.income to ext.onIncome),
                "incomeContainer" to (ext.incomeContainer to ext.onIncomeContainer),
                "expense" to (ext.expense to ext.onExpense),
                "expenseContainer" to (ext.expenseContainer to ext.onExpenseContainer),
            )
        }
        Section("Typography") {
            val t = MaterialTheme.typography
            listOf(
                "displayLarge" to t.displayLarge, "displayMedium" to t.displayMedium, "displaySmall" to t.displaySmall,
                "headlineLarge" to t.headlineLarge, "headlineMedium" to t.headlineMedium, "headlineSmall" to t.headlineSmall,
                "titleLarge" to t.titleLarge, "titleMedium" to t.titleMedium, "titleSmall" to t.titleSmall,
                "bodyLarge" to t.bodyLarge, "bodyMedium" to t.bodyMedium, "bodySmall" to t.bodySmall,
                "labelLarge" to t.labelLarge, "labelMedium" to t.labelMedium, "labelSmall" to t.labelSmall,
            ).forEach { (name, style) -> TypeSample(name, style) }
        }
        Section("Shapes") {
            val s = MaterialTheme.shapes
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.s),
                verticalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                ShapeSample("extraSmall 4", s.extraSmall)
                ShapeSample("small 8", s.small)
                ShapeSample("medium 16", s.medium)
                ShapeSample("large 24", s.large)
                ShapeSample("extraLarge 32", s.extraLarge)
            }
        }
        Section("Spacing") {
            listOf(
                "xxs" to Spacing.xxs, "xs" to Spacing.xs, "s" to Spacing.s, "m" to Spacing.m,
                "l" to Spacing.l, "xl" to Spacing.xl, "xxl" to Spacing.xxl, "xxxl" to Spacing.xxxl,
            ).forEach { (name, size) -> SpacingSample(name, size) }
        }
    }
}

@Composable
private fun Section(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        content()
    }
}

@Composable
private fun ColorRow(vararg items: Pair<String, Pair<Color, Color>>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        items.forEach { (name, colors) ->
            Box(
                modifier = Modifier
                    .size(width = 104.dp, height = 64.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(colors.first)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.small)
                    .padding(Spacing.xxs),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = name, style = MaterialTheme.typography.labelSmall, color = colors.second)
            }
        }
    }
}

@Composable
private fun TypeSample(name: String, style: TextStyle) {
    Text(text = name, style = style, color = MaterialTheme.colorScheme.onSurface)
}

@Composable
private fun ShapeSample(name: String, shape: Shape) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, shape)
        )
        Text(text = name, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun SpacingSample(name: String, size: Dp) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        Text(
            modifier = Modifier.width(48.dp),
            text = name,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Box(
            modifier = Modifier
                .width(size)
                .height(16.dp)
                .background(MaterialTheme.colorScheme.tertiary)
        )
        Text(text = "${size.value.toInt()} dp", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@ThemePreviews
@Composable
private fun ThemeCatalogPreview() {
    MoneyDeskTheme {
        Surface(modifier = Modifier.fillMaxWidth()) { ThemeCatalog() }
    }
}
