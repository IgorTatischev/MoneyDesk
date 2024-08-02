package com.money.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BaseTextField(
    value: String,
    @StringRes label: Int,
    icon: ImageVector,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(15.dp),
        label = { Text(text = stringResource(id = label)) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
    )
}