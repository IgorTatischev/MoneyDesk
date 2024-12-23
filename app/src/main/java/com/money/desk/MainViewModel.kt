package com.money.desk

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.money.datastore.onboarding.OnBoardingDataStore
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val onboardingDataStore: OnBoardingDataStore): ViewModel() {

    private val _onboardingCheck = mutableStateOf(false)
    val onboardingCheck: State<Boolean> = _onboardingCheck

    init {
        viewModelScope.launch {
            onboardingDataStore.getOnboardingCheck.collect {
                _onboardingCheck.value = it
            }
        }
    }
}