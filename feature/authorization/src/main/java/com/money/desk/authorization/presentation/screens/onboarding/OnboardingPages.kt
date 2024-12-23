package com.money.desk.authorization.presentation.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.money.authorization.R
import com.money.ui.R as CommonR

@Immutable
internal enum class OnboardingPages(
    @DrawableRes val image: Int,
    @StringRes val titleText: Int,
    @StringRes val descriptionText: Int
) {
    MAIN(CommonR.drawable.onboarding, R.string.main_page_title, R.string.main_page_text),
    CATEGORY(CommonR.drawable.onboarding, R.string.category_page_title, R.string.category_page_text),
    WALLET(CommonR.drawable.onboarding, R.string.wallet_page_title, R.string.wallet_page_text),
    ANALYTICS(CommonR.drawable.onboarding, R.string.analytics_page_title, R.string.analytics_page_text);

    companion object {
        fun getDefault() = MAIN
    }
}