package com.money.categories.domain.model

import androidx.annotation.Keep

@Keep
data class CategoryModel(
    val name: String = "",
    val isDefault: Boolean = false,
    val color: String = "",
    val iconChar: String = ""
)
