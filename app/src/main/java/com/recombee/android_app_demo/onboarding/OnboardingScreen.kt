package com.recombee.android_app_demo.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recombee.android_app_demo.NavigationActions
import com.recombee.android_app_demo.R

@Composable
fun OnboardingScreen(
    navActions: NavigationActions,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    // only get the value once to prevent flashes when the value is updated
    val vmOnboardingShown by viewModel.onboardingShown.collectAsStateWithLifecycle(null)
    val onboardingShown by remember(vmOnboardingShown != null) {
        mutableStateOf(vmOnboardingShown ?: false)
    }

    Surface(
        Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Column(verticalArrangement = Arrangement.spacedBy(48.dp)) {
                Image(
                    painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Recomflix logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = if (onboardingShown) "About the" else "Welcome to the",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        text = "Recombee Android Demo",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        "This application showcases the integration of Recombee's recommendation API within an Android application, utilizing a dataset comprised of movies.",
                        "The home screen shows the \"Items to User\" scenario (Top Picks for You).",
                        "By tapping on a movie, you can either see \"Items to Item\" recommendations (Related Movies) or send interactions (e.g. rating or how much of a movie has been watched).",
                        "After sending interactions, you can pull to refresh any movie list. The recommended movies are updated to reflect this new data.",
                    ).map {
                        Text(it)
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (onboardingShown) {
                    Button(onClick = { navActions.goBack() }) {
                        Text("Go back")
                    }
                } else {
                    Button(onClick = { viewModel.setOnboardingShown() }) {
                        Text("Let's go!")
                    }
                }
            }
        }
    }
}