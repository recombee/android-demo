package com.recombee.android_app_demo

import androidx.navigation.NavHostController


object Routes {
    const val MAIN = "main"
    const val ITEM = "item/{${RouteArgs.ID}}?${RouteArgs.RECOMM_ID}={${RouteArgs.RECOMM_ID}}"
    const val SETTINGS = "settings"
    const val ONBOARDING = "onboarding"
    fun item(id: String, recommId: String?) = "item/$id".plus(
        if (recommId != null) "?${RouteArgs.RECOMM_ID}=$recommId" else ""
    )
}

object RouteArgs {
    const val ID = "id"
    const val RECOMM_ID = "recomm_id"
}

class NavigationActions(private val navController: NavHostController) {
    fun goBack() {
        if (navController.currentBackStackEntry?.destination?.route != Routes.MAIN) {
            navController.popBackStack()
        }
    }


    fun navigateToItem(id: String, recommId: String?) {
        navController.navigate(Routes.item(id, recommId))
    }


    fun navigateToSettings() {
        navController.navigate(Routes.SETTINGS)
    }

    fun navigateToOnboarding() {
        navController.navigate(Routes.ONBOARDING)
    }

}
