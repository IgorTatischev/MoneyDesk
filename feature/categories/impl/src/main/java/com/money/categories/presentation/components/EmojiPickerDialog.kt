package com.money.categories.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.emoji.EmojiDialog
import com.maxkeppeler.sheets.emoji.models.EmojiCategoryAppearance
import com.maxkeppeler.sheets.emoji.models.EmojiConfig
import com.maxkeppeler.sheets.emoji.models.EmojiProvider
import com.maxkeppeler.sheets.emoji.models.EmojiSelection

internal const val DefaultCategoryEmoji = "☕️"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmojiPickerDialog(
    onEmojiSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    EmojiDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = { onDismiss() }),
        selection = EmojiSelection.Unicode(
            onPositiveClick = { emojiUnicode -> onEmojiSelected(emojiUnicode) }
        ),
        config = EmojiConfig(
            categoryAppearance = EmojiCategoryAppearance.TEXT,
            emojiProvider = EmojiProvider.IOS
        )
    )
}
