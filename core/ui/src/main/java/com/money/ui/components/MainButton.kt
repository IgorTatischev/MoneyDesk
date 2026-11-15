package com.money.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.money.ui.theme.MoneyDeskTheme
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

@Preview
@Composable
private fun MainButtonPreview() {
    MoneyDeskTheme {
        MainButton(onClick = {}, text = "Send", loading = true)
    }
}
