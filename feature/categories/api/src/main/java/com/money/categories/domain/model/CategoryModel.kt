package com.money.categories.domain.model

import androidx.annotation.Keep

@Keep
data class CategoryModel(
    /** Firestore document id; filled by the repository, never written as a field. */
    val id: String = "",
    val name: String = "",
    val isDefault: Boolean = false,
    val color: String = "",
    val iconChar: String = ""
)
