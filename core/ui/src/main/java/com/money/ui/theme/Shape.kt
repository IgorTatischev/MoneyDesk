package com.money.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Шкала скруглений MoneyDesk: 4 / 8 / 16 / 24 / 32 dp.
 * Подключена в [MoneyDeskTheme], стандартные компоненты M3 берут её автоматически.
 */
val MoneyDeskShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp),
)