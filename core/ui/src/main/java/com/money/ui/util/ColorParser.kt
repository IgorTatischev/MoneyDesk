package com.money.ui.util

import androidx.compose.ui.graphics.Color

/**
 * Parses a category color stored as a hex string.
 * Supported: "#RRGGBB", "#AARRGGBB", "RRGGBB", "AARRGGBB", "0xAARRGGBB".
 * Returns null for empty or malformed values.
 */
fun String.toComposeColorOrNull(): Color? {
    val hex = trim()
        .removePrefix("#")
        .removePrefix("0x")
        .removePrefix("0X")

    val argb = when (hex.length) {
        6 -> hex.toLongOrNull(16)?.or(0xFF000000)
        8 -> hex.toLongOrNull(16)
        else -> null
    } ?: return null

    return Color(argb.toInt())
}

/**
 * Formats an ARGB color int as "#RRGGBB" (alpha is dropped) for storing a category color.
 */
fun Int.toHexColorString(): String = "#%06X".format(this and 0xFFFFFF)
