package com.money.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import com.money.ui.util.ThemePreviews
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.money.ui.theme.MoneyDeskTheme
import com.money.ui.theme.Spacing
import com.money.ui.util.ButtonsDefaults
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonsDefaults.MediumButtonPadding,
    content: @Composable () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    val shape = ButtonsDefaults.DefaultButtonShape
    val shadowColor = if (enabled) MaterialTheme.colorScheme.onBackground else Color.Gray

    val animSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessHigh
    )
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val currentOffsetX = remember { Animatable(1f) }
    val currentOffsetY = remember { Animatable(1f) }

    LaunchedEffect(key1 = pressed.value) {
        if (pressed.value) {
            launch {
                currentOffsetX.animateTo(0f, animSpec)
            }
            launch {
                currentOffsetY.animateTo(0f, animSpec)
            }
        } else {
            launch {
                currentOffsetX.animateTo(1f, animSpec)
            }
            launch {
                currentOffsetY.animateTo(1f, animSpec)
            }
        }
    }

    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        Button(
            modifier = modifier
                .drawBehind {
                    drawOutline(
                        outline = shape.createOutline(size, layoutDirection, this),
                        color = shadowColor,
                    )
                }
                .graphicsLayer {
                    this.translationX =
                        -(ButtonsDefaults.MainButtonOffset.offsetX.toPx() * currentOffsetX.value)
                    this.translationY =
                        -(ButtonsDefaults.MainButtonOffset.offsetY.toPx() * currentOffsetX.value)
                },
            onClick = onClick,
            contentPadding = contentPadding,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                disabledContentColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            shape = shape,
            enabled = enabled,
            border = BorderStroke(
                1.dp,
                if (enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.surfaceContainer
            ),
            interactionSource = interactionSource
        ) {
            content()
        }
    }
}

@Composable
fun OnboardingButtonSmall(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) = OnboardingButton(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    contentPadding = ButtonsDefaults.SmallButtonPadding,
    content = content
)

@Composable
fun OnboardingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    loading: Boolean = false,
    iconLeft: Painter? = null,
    iconRight: Painter? = null,
) = OnboardingButton(modifier = modifier, onClick = { if (!loading) onClick() }, enabled = enabled) {
    ButtonsDefaults.ButtonContentRow {
        if (loading) {
            ButtonsDefaults.LoadingIndicator(color = MaterialTheme.colorScheme.onPrimary)
        } else {
            iconLeft?.let {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = it,
                    contentDescription = null
                )
            }
            Text(
                text = text,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
            )
            iconRight?.let {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = it,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun OnboardingButtonSmall(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    loading: Boolean = false,
    iconLeft: Painter? = null,
    iconRight: Painter? = null,
) = OnboardingButtonSmall(
    modifier = modifier,
    onClick = { if (!loading) onClick() },
    enabled = enabled,
) {
    ButtonsDefaults.ButtonContentRow {
        if (loading) {
            ButtonsDefaults.LoadingIndicator(color = MaterialTheme.colorScheme.onPrimary)
        } else {
            iconLeft?.let {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = it,
                    contentDescription = null
                )
            }
            Text(
                text = text,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall,
            )
            iconRight?.let {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = it,
                    contentDescription = null
                )
            }
        }
    }
}


@ThemePreviews
@Composable
private fun OnboardingButtonPreview() {
    MoneyDeskTheme {
        Surface {
            Column(
                modifier = Modifier.padding(Spacing.m),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                OnboardingButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = {},
                ) {
                    Text(text = "first")
                }
                OnboardingButtonSmall(
                    modifier = Modifier,
                    onClick = {},
                    text = "second",
                    enabled = false,
                )
                OnboardingButtonSmall(
                    modifier = Modifier,
                    onClick = {},
                    loading = true,
                    text = "second",
                    enabled = true,
                )
            }
        }
    }
}
