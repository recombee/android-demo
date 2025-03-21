package com.recombee.android_app_demo

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.recombee.android_app_demo.item.ItemScreen
import com.recombee.android_app_demo.main.MainScreen
import com.recombee.android_app_demo.onboarding.OnboardingScreen
import com.recombee.android_app_demo.onboarding.OnboardingViewModel
import com.recombee.android_app_demo.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: Route = Route.Main,
    navActions: NavigationActions = remember(navController) { NavigationActions(navController) },
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { enterTransition(this) },
        exitTransition = { exitTransition(this) },
        popEnterTransition = { popEnterTransition(this) },
        popExitTransition = { popExitTransition(this) },
    ) {
        composable<Route.Main> { MainRoute(navActions) }
        composable<Route.Item> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.Item>()
            ItemScreen(navActions, route.id, route.recommId)
        }
        composable<Route.Settings> { SettingsScreen(navActions) }
        composable<Route.Onboarding> { OnboardingScreen(navActions) }
    }
}

@Composable
fun MainRoute(navActions: NavigationActions, viewModel: OnboardingViewModel = hiltViewModel()) {
    val onboardingShown by viewModel.onboardingShown.collectAsStateWithLifecycle(true)

    Crossfade(targetState = onboardingShown, label = "Main") { target ->
        if (!target) {
            OnboardingScreen(navActions, viewModel)
        } else {
            MainScreen(navActions)
        }
    }
}

fun enterTransition(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(200),
    )
}

fun exitTransition(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(200),
    )
}

fun popEnterTransition(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(200),
    )
}

fun popExitTransition(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(200),
    )
}
