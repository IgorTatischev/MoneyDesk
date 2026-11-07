package com.money.datastore.onboarding

import kotlinx.coroutines.flow.Flow

interface OnBoardingDataStore {

    suspend fun saveOnboardingCheck(isChecked: Boolean)

    val getOnboardingCheck: Flow<Boolean>

}