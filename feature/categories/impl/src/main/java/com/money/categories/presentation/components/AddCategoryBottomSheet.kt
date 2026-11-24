package com.money.categories.presentation.components

import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.SheetValue
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.money.categories.impl.R
import com.money.categories.presentation.screens.categories_screen.AddCategorySheetState
import com.money.ui.components.AlternativeBaseTextField
import com.money.ui.components.MainButton
import com.money.ui.data.CategoryTabs
import com.money.ui.theme.MoneyDeskTheme
import com.money.ui.util.ButtonsDefaults
import com.money.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCategoryBottomSheet(
    state: AddCategorySheetState,
    /** false once the screen closed the sheet: it then animates out and calls [onHidden]. */
    visible: Boolean,
    onDismiss: () -> Unit,
    onHidden: () -> Unit,
    onNameChange: (String) -> Unit,
    onTypeChange: (CategoryTabs) -> Unit,
    onColorChange: (Int) -> Unit,
    onEmojiChange: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
) {
    // While saving/deleting the user can't swipe/tap the sheet away (the VM ignores dismiss
    // then, so the sheet would end up hidden but still "open").
    val blockUserDismiss by rememberUpdatedState(visible && state.isBusy)
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden || !blockUserDismiss },
    )
    val currentOnHidden by rememberUpdatedState(onHidden)

    LaunchedEffect(visible) {
        if (!visible) {
            sheetState.hide()
            currentOnHidden()
        }
    }
    var showColorPicker by rememberSaveable { mutableStateOf(false) }
    var showEmojiPicker by rememberSaveable { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        AddCategorySheetContent(
            state = state,
            onNameChange = onNameChange,
            onTypeChange = onTypeChange,
            onEmojiClick = { showEmojiPicker = true },
            onColorClick = { showColorPicker = true },
            onSave = onSave,
            onDelete = onDelete,
        )
    }

    if (showColorPicker) {
        ColorPickerDialog(
            initialColor = state.color,
            onColorSelected = onColorChange,
            onDismiss = { showColorPicker = false },
        )
    }

    if (showEmojiPicker) {
        EmojiPickerDialog(
            onEmojiSelected = onEmojiChange,
            onDismiss = { showEmojiPicker = false },
        )
    }
}

/** Sheet body, separate from [ModalBottomSheet] so it can be shown in previews. */
@Composable
private fun AddCategorySheetContent(
    state: AddCategorySheetState,
    onNameChange: (String) -> Unit,
    onTypeChange: (CategoryTabs) -> Unit,
    onEmojiClick: () -> Unit,
    onColorClick: () -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val color = Color(state.color)
    val tabs = CategoryTabs.entries

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .padding(horizontal = Spacing.m)
            .padding(bottom = Spacing.m),
        verticalArrangement = Arrangement.spacedBy(Spacing.m),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(
                if (state.isEditMode) R.string.edit_category else R.string.new_category
            ),
            style = MaterialTheme.typography.titleMedium,
        )

        // Category preview: tap to pick an emoji.
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(color)
                .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                .clickable(onClick = onEmojiClick),
            contentAlignment = Alignment.Center,
        ) {
            EmojiText(text = state.emoji)
        }

        AlternativeBaseTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            onValueChange = onNameChange,
            // The name identifies a category, so it is fixed once created.
            enabled = !state.isEditMode,
            label = stringResource(R.string.category_name),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onEmojiClick,
            ) {
                Text(text = stringResource(R.string.category_icon))
                Text(modifier = Modifier.padding(start = Spacing.xs), text = state.emoji)
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onColorClick,
            ) {
                Text(text = stringResource(R.string.category_color))
                Box(
                    modifier = Modifier
                        .padding(start = Spacing.xs)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
                )
            }
        }

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            tabs.forEachIndexed { index, tab ->
                SegmentedButton(
                    selected = state.type == tab,
                    onClick = { onTypeChange(tab) },
                    enabled = !state.isEditMode,
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = tabs.size),
                    icon = {},
                ) {
                    Text(text = stringResource(tab.textId))
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            if (state.isEditMode) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { if (!state.isBusy) onDelete() },
                    enabled = !state.isSaving,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    ),
                ) {
                    if (state.isDeleting) {
                        ButtonsDefaults.LoadingIndicator(
                            color = MaterialTheme.colorScheme.onErrorContainer,
                        )
                    } else {
                        Text(text = stringResource(R.string.delete), maxLines = 1)
                    }
                }
            }
            MainButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    focusManager.clearFocus()
                    onSave()
                },
                text = stringResource(if (state.isEditMode) R.string.update else R.string.save),
                enabled = state.canSave || state.isSaving,
                loading = state.isSaving,
            )
        }
    }
}

private class AddCategorySheetPreviewProvider : PreviewParameterProvider<AddCategorySheetState> {
    override val values = sequenceOf(
        AddCategorySheetState(type = CategoryTabs.Expense),
        AddCategorySheetState(type = CategoryTabs.Income, name = "Salary", emoji = "💰"),
        AddCategorySheetState(
            type = CategoryTabs.Expense,
            name = "Groceries",
            color = 0xFF66BB6A.toInt(),
            emoji = "🛒",
            editingCategoryId = "preview",
        ),
        AddCategorySheetState(
            type = CategoryTabs.Expense,
            name = "Groceries",
            emoji = "🛒",
            editingCategoryId = "preview",
            isDeleting = true,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun AddCategorySheetContentPreview(
    @PreviewParameter(AddCategorySheetPreviewProvider::class) state: AddCategorySheetState,
) {
    MoneyDeskTheme {
        Surface {
            AddCategorySheetContent(
                state = state,
                onNameChange = {},
                onTypeChange = {},
                onEmojiClick = {},
                onColorClick = {},
                onSave = {},
                onDelete = {},
            )
        }
    }
}
