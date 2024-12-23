package com.money.desk.authorization.presentation.screens.onboarding

import androidx.compose.runtime.Immutable

@Immutable
internal data class OnboardingScreenState(
    val currentPage: OnboardingPages = OnboardingPages.getDefault(),
)

internal sealed class OnboardingScreenEvent {
    data object NavigateToAuth : OnboardingScreenEvent()
    data class ChangePage(val page: OnboardingPages) : OnboardingScreenEvent()
    data class OnBackClick(val currentPage: OnboardingPages) : OnboardingScreenEvent()
}

internal sealed class OnboardingScreenEffect {
    data object NavigateToAuthScreen: OnboardingScreenEffect()
}
