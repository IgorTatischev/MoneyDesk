package com.money.categories.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.emoji2.text.EmojiCompat

@Composable
fun EmojiText(text: String) {
    val processedText = remember(text) { text.processEmojiOrSelf() }
    Text(text = processedText, fontSize = 36.sp)
}

/**
 * EmojiCompat isn't configured in Compose previews (and may still be loading at runtime),
 * where [EmojiCompat.get]/[EmojiCompat.process] would throw — fall back to the raw text.
 */
private fun String.processEmojiOrSelf(): String {
    if (!EmojiCompat.isConfigured()) return this
    val emojiCompat = EmojiCompat.get()
    if (emojiCompat.loadState != EmojiCompat.LOAD_STATE_SUCCEEDED) return this
    return emojiCompat.process(this)?.toString() ?: this
}