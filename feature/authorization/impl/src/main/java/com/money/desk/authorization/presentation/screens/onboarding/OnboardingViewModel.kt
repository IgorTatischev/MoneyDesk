package com.money.desk.authorization.presentation.screens.onboarding

import androidx.lifecycle.viewModelScope
import com.money.common.BaseViewModel
import com.money.datastore.onboarding.OnBoardingDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val onboardingDataStore: OnBoardingDataStore,
) : BaseViewModel<OnboardingScreenState, OnboardingScreenEvent, OnboardingScreenEffect>(initialState = OnboardingScreenState()) {

    override fun handleEvent(event: OnboardingScreenEvent) {
        when (event) {
            is OnboardingScreenEvent.ChangePage -> changePageScreenHandler(event.page)

            is OnboardingScreenEvent.OnBackClick -> onBackClickHandler(event.currentPage)

            is OnboardingScreenEvent.NavigateToAuth -> {
                viewModelScope.launch { onboardingDataStore.saveOnboardingCheck(true) }
                sendEffect(OnboardingScreenEffect.NavigateToAuthScreen)
            }
        }
    }

    private fun changePageScreenHandler(page: OnboardingPages) {
        updateState {
            copy(
                currentPage = page
            )
        }
    }

    private fun onBackClickHandler(currentPage: OnboardingPages) {
        updateState {
            copy(
                currentPage = when (currentPage) {
                    OnboardingPages.ANALYTICS -> OnboardingPages.WALLET
                    OnboardingPages.WALLET -> OnboardingPages.CATEGORY
                    OnboardingPages.CATEGORY -> OnboardingPages.MAIN
                    else -> currentPage
                }
            )
        }
    }
}