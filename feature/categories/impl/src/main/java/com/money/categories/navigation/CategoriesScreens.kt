package com.money.categories.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed class CategoriesScreens {
    @Serializable
    data object CategoryScreen: CategoriesScreens()
}