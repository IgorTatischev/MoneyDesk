package com.money.ui.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/** Один `@Preview` в двух темах: светлая и тёмная. Оборачивать содержимое в MoneyDeskTheme и Surface. */
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class ThemePreviews

/** То же для полноэкранных превью. */
@Preview(name = "Light", showBackground = true, showSystemUi = false)
@Preview(name = "Dark", showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class ScreenPreviews
