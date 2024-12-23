package com.money.datastore.onboarding

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("OnboardingCheck")

internal class OnBoardingDataStoreImpl @Inject constructor(@ApplicationContext context: Context): OnBoardingDataStore {

    private val onBoardingDataStore = context.dataStore

    companion object {
        val ONBOARDING_CHECK_KEY = booleanPreferencesKey("onboarding_check")
    }

    override suspend fun saveOnboardingCheck(isChecked: Boolean) {
        onBoardingDataStore.edit { preferences ->
            preferences[ONBOARDING_CHECK_KEY] = isChecked
        }
    }

    override val getOnboardingCheck: Flow<Boolean> = onBoardingDataStore.data.map { preferences ->
        preferences[ONBOARDING_CHECK_KEY] ?: false
    }

}