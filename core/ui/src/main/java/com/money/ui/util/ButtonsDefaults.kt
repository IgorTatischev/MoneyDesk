package com.money.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ButtonsDefaults {

    val MediumButtonPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)

    val SmallButtonPadding = PaddingValues(horizontal = 32.dp, vertical = 14.dp)

    @Composable
    fun ButtonContentRow(
        modifier: Modifier = Modifier,
        content: @Composable RowScope.() -> Unit
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }

    @Composable
    fun LoadingIndicator(
        color: Color
    ) {
        CircularProgressIndicator(
            strokeWidth = 3.dp,
            strokeCap = StrokeCap.Round,
            modifier = Modifier.size(22.dp),
            color = color
        )
    }

    data object MainButtonOffset {
        val offsetX: Dp = 4.dp
        val offsetY: Dp = 4.dp
    }

    val DefaultButtonShape = RoundedCornerShape(24.dp)
    val cornerRadius = 24.dp
}

fun Dp.dpToPx(density: Density) = with(density) { this@dpToPx.toPx() }
