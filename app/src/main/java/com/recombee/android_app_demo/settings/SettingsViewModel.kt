package com.recombee.android_app_demo.settings

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recombee.android_app_demo.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userSettings: DataStore<UserSettings>) :
    ViewModel() {
    val userId = userSettings.data.map { it.userId }

    fun resetUserId() {
        viewModelScope.launch {
            userSettings.updateData {
                it.toBuilder().setUserId(UUID.randomUUID().toString()).build()
            }
        }
    }

    fun resetOnboardingShown() {
        viewModelScope.launch {
            userSettings.updateData { it.toBuilder().setOnboardingShown(false).build() }
        }
    }
}
