package com.money.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.money.ui.theme.MoneyDeskTheme
import com.money.ui.util.TextFieldsDefaults
import com.money.ui.util.TextFieldsDefaults.getTextColor
import com.money.ui.util.TextFieldsDefaults.maxTextFieldHeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    error: String? = null,
    label: String? = null,
    textStyle: TextStyle = TextFieldsDefaults.defaultTextStyle,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldsDefaults.defaultColors,
    containerShape: Shape = TextFieldsDefaults.DefaultTextFieldShape,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    ) {
    val interactionSource = remember { MutableInteractionSource() }
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.getTextColor(enabled, error != null, interactionSource).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = mergedTextStyle,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = enabled,
            label = label?.takeIf { it.isNotBlank() && value.isNotEmpty() }?.let {
                {
                    Text(text = it, style = TextFieldsDefaults.labelTextStyle)
                }
            },
            singleLine = singleLine,
            supportingText = error?.takeIf { it.isNotBlank() }?.let {
                {
                    Text(text = it, style = TextFieldsDefaults.errorTextStyle)
                }
            },
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            leadingIcon = leadingIcon?.let {
                {
                    Box(modifier = Modifier.sizeIn(maxWidth = 30.dp, maxHeight = 30.dp)) { it() }
                }
            },
            trailingIcon = trailingIcon?.let {
                {
                    Box(modifier = Modifier.sizeIn(maxWidth = 30.dp, maxHeight = 30.dp)) { it() }
                }
            },
            placeholder = placeholder,
            colors = colors,
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = enabled,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = colors,
                    shape = containerShape
                )
            },
        )
    }
}

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    error: String? = null,
    textStyle: TextStyle = TextFieldsDefaults.defaultTextStyle,
    hint: String? = null,
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldsDefaults.defaultColors,
    containerShape: Shape = TextFieldsDefaults.DefaultTextFieldShape,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    SimpleTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        error = error,
        label = label,
        textStyle = textStyle,
        placeholder = (hint?.takeIf { it.isNotBlank() } ?: label?.takeIf { it.isNotBlank() })?.let {
            {
                Text(
                    text = it,
                    style = textStyle,
                    color = MaterialTheme.colorScheme.surfaceContainer
                )
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = colors,
        containerShape = containerShape,
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        readOnly = readOnly,
    )

}

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    textStyle: TextStyle = TextFieldsDefaults.defaultTextStyle,
    hint: String? = null,
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldsDefaults.defaultColors,
    containerShape: Shape = TextFieldsDefaults.DefaultTextFieldShape,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
) {
    SimpleTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = label,
        textStyle = textStyle,
        placeholder = (hint?.takeIf { it.isNotBlank() } ?: label?.takeIf { it.isNotBlank() })?.let {
            {
                Text(
                    text = it,
                    style = textStyle,
                    color = MaterialTheme.colorScheme.surfaceContainer
                )
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = colors,
        containerShape = containerShape,
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
        visualTransformation = visualTransformation
    )

}

@Composable
@Preview
private fun Preview() {
    MoneyDeskTheme {
        Surface {
            Column {
                SimpleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = "123",
                    label = "Label",
                    hint = "Hint",
                    onValueChange = {}
                )

                val maxHeight = maxTextFieldHeight.dp
                var textFieldHeightPx by remember { mutableFloatStateOf(0f) }
                val textFieldHeightDp = with(LocalDensity.current) { textFieldHeightPx.toDp() }
                var showLabel by remember { mutableStateOf(true) }

                SimpleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 156.dp, max = maxHeight)
                        .onGloballyPositioned { coordinates ->
                            textFieldHeightPx = coordinates.size.height.toFloat()
                        }
                        .onSizeChanged {
                            showLabel = textFieldHeightDp < maxHeight - 0.5.dp
                        },
                    containerShape = TextFieldsDefaults.LargeTextFieldShape,
                    value = "Value",
                    onValueChange = {  },
                    label = if (showLabel) "Hint" else null,
                )

            }
        }
    }
}