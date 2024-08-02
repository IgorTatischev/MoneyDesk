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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.money.ui.theme.MoneyDeskTheme
import com.money.ui.util.ButtonsDefaults
import com.money.ui.util.dpToPx
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonsDefaults.MediumButtonPadding,
    content: @Composable () -> Unit,
) {
    val cornerRadiusPx = ButtonsDefaults.cornerRadius.dpToPx(LocalDensity.current)
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

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
            modifier = modifier
                .drawBehind {
                    drawRoundRect(
                        cornerRadius = CornerRadius(x = cornerRadiusPx, y = cornerRadiusPx),
                        //todo color
                        color = if (enabled) Color.Black else Color.Gray,
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
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                disabledContentColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            shape = ButtonsDefaults.DefaultButtonShape,
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
fun MainButtonSmall(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) = MainButton(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    contentPadding = ButtonsDefaults.SmallButtonPadding,
    content = content
)

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    loading: Boolean = false,
    iconLeft: Painter? = null,
    iconRight: Painter? = null,
) = MainButton(modifier = modifier, onClick = { if (!loading) onClick() }, enabled = enabled) {
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
fun MainButtonSmall(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    loading: Boolean = false,
    iconLeft: Painter? = null,
    iconRight: Painter? = null,
) = MainButtonSmall(
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


@Preview
@Composable
private fun MainButtonPreview() {
    MoneyDeskTheme {
        Column(
            modifier = Modifier
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MainButton(
                modifier = Modifier.wrapContentSize(),
                onClick = {},
            ) {
                Text(text = "first")
            }
            MainButtonSmall(
                modifier = Modifier,
                onClick = {},
                text = "second",
                enabled = false,
            )
            MainButtonSmall(
                modifier = Modifier,
                onClick = {},
                loading = true,
                text = "second",
                enabled = true,
            )
        }
    }
}