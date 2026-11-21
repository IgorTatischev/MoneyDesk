package com.money.categories.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.color.ColorDialog
import com.maxkeppeler.sheets.color.models.ColorConfig
import com.maxkeppeler.sheets.color.models.ColorSelection
import com.maxkeppeler.sheets.color.models.ColorSelectionMode
import com.maxkeppeler.sheets.color.models.MultipleColors
import com.maxkeppeler.sheets.color.models.SingleColor

private val CategoryPalette = intArrayOf(
    0xFFEF5350.toInt(), // red
    0xFFFF7043.toInt(), // deep orange
    0xFFFFA726.toInt(), // orange
    0xFFFFCA28.toInt(), // amber
    0xFF9CCC65.toInt(), // light green
    0xFF66BB6A.toInt(), // green
    0xFF26A69A.toInt(), // teal
    0xFF26C6DA.toInt(), // cyan
    0xFF42A5F5.toInt(), // blue
    0xFF5C6BC0.toInt(), // indigo
    0xFFAB47BC.toInt(), // purple
    0xFFEC407A.toInt(), // pink
    0xFF8D6E63.toInt(), // brown
    0xFF78909C.toInt(), // blue grey
)

internal val DefaultCategoryColor: Int = CategoryPalette.first()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ColorPickerDialog(
    initialColor: Int,
    onColorSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    ColorDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = { onDismiss() }),
        selection = ColorSelection(
            selectedColor = SingleColor(initialColor),
            onSelectColor = onColorSelected,
        ),
        config = ColorConfig(
            templateColors = MultipleColors.ColorsInt(*CategoryPalette),
            defaultDisplayMode = ColorSelectionMode.TEMPLATE,
            allowCustomColorAlphaValues = false,
        ),
    )
}
