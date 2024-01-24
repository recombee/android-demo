package com.recombee.android_app_demo.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recombee.android_app_demo.BuildConfig
import com.recombee.android_app_demo.NavigationActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navActions: NavigationActions,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val userId by viewModel.userId.collectAsStateWithLifecycle(initialValue = "")

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Settings")
                },
                navigationIcon = {
                    IconButton(onClick = { navActions.goBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("User Settings", style = MaterialTheme.typography.headlineSmall)
            Column(
                Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("User ID:\n$userId")
                Button(onClick = { viewModel.resetUserId() }) {
                    Text("Reset User ID")
                }
                if (BuildConfig.DEBUG) {
                    Button(onClick = { viewModel.resetOnboardingShown() }) {
                        Text("Reset Onboarding")
                    }
                }
            }
        }
    }
}
