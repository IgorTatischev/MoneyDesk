package com.money.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.money.ui.util.ThemePreviews
import com.money.ui.theme.MoneyDeskTheme
import com.money.ui.theme.Spacing
import com.money.ui.util.ButtonsDefaults

/**
 * Обычная кнопка, которая на время загрузки показывает лоадер вместо текста.
 * Пока [loading] == true, нажатия игнорируются.
 */
@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    Button(
        modifier = modifier,
        onClick = { if (!loading) onClick() },
        enabled = enabled,
    ) {
        if (loading) {
            ButtonsDefaults.LoadingIndicator(color = MaterialTheme.colorScheme.onPrimary)
        } else {
            Text(text = text, maxLines = 1)
        }
    }
}

@ThemePreviews
@Composable
private fun MainButtonPreview() {
    MoneyDeskTheme {
        Surface {
            Column(
                modifier = Modifier.padding(Spacing.m),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                MainButton(onClick = {}, text = "Send")
                MainButton(onClick = {}, text = "Send", enabled = false)
                MainButton(onClick = {}, text = "Send", loading = true)
            }
        }
    }
}
