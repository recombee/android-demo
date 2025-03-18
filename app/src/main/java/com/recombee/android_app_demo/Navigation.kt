package com.recombee.android_app_demo

import androidx.annotation.Keep
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Keep
@Serializable
sealed class Route {
    @Keep @Serializable data object Main : Route()

    @Keep @Serializable data class Item(val id: String, val recommId: String?) : Route()

    @Keep @Serializable data object Settings : Route()

    @Keep @Serializable data object Onboarding : Route()
}

class NavigationActions(private val navController: NavHostController) {
    fun goBack() {
        if (navController.currentBackStackEntry?.destination?.hasRoute(Route.Main::class) == true) {
            return
        }
        navController.popBackStack()
    }

    fun navigateToItem(id: String, recommId: String?) {
        navController.navigate(Route.Item(id, recommId))
    }

    fun navigateToSettings() {
        navController.navigate(Route.Settings)
    }

    fun navigateToOnboarding() {
        navController.navigate(Route.Onboarding)
    }
}
