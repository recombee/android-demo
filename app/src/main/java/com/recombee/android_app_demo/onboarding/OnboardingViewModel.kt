package com.recombee.android_app_demo.onboarding

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recombee.android_app_demo.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userSettings: DataStore<UserSettings>,
) : ViewModel() {
    val onboardingShown = userSettings.data.map { it.onboardingShown }

    fun setOnboardingShown() {
        viewModelScope.launch {
            userSettings.updateData {
                it.toBuilder()
                    .setOnboardingShown(true)
                    .build()
            }
        }
    }
}