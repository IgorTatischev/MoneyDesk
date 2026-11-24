package com.money.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.money.ui.R
import com.money.ui.theme.MoneyDeskTheme
import com.money.ui.theme.Spacing
import com.money.ui.util.ThemePreviews

@Composable
fun BaseTextField(
    value: String,
    @StringRes label: Int,
    icon: ImageVector,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        label = { Text(text = stringResource(id = label)) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        isError = isError
    )
}

@ThemePreviews
@Composable
private fun BaseTextFieldPreview() {
    MoneyDeskTheme {
        Surface {
            Column(
                modifier = Modifier.padding(Spacing.m),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                BaseTextField(value = "user@mail.com", label = R.string.expense, icon = Icons.Outlined.AccountCircle, onValueChange = {})
                BaseTextField(value = "", label = R.string.expense, icon = Icons.Outlined.AccountCircle, isError = true, onValueChange = {})
            }
        }
    }
}
