package com.money.ui.util

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object TextFieldsDefaults {

    fun Modifier.userFieldLarge() = this.heightIn(min = 156.dp, max = 200.dp)

    val LargeTextFieldShape: Shape
        @Composable
        get() = MaterialTheme.shapes.large

    val DefaultTextFieldShape: Shape
        @Composable
        get() = CircleShape

    val maxTextFieldHeight
        @Composable
        get(): Int {
            val configuration = LocalConfiguration.current
            return (configuration.screenHeightDp / 2) - 70
        }

    val defaultTextStyle
        @Composable
        get() = MaterialTheme.typography.bodyMedium.copy(lineHeight = 19.sp)

    val labelTextStyle
        @Composable
        get() = MaterialTheme.typography.labelSmall

    val errorTextStyle
        @Composable
        get() = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
            color = MaterialTheme.colorScheme.onError
        )

    val defaultColors
        @Composable
        get() = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            // Border colors match OutlinedTextField defaults (see BaseTextField).
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            // Label follows the border color.
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.outline,
            errorLabelColor = MaterialTheme.colorScheme.error,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            // Placeholder (hint) matches the label color.
            focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            errorPlaceholderColor = MaterialTheme.colorScheme.error,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            disabledIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )

    @Composable
    fun TextFieldColors.getTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledTextColor
            isError -> errorTextColor
            focused -> focusedTextColor
            else -> unfocusedTextColor
        }
        return rememberUpdatedState(targetValue)
    }
}