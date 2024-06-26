package com.example.assignment1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.assignment1.utility.AppViewModelProvider
import com.example.assignment1.ui.screens.active_timer_screen.ActivePresetDestination
import com.example.assignment1.ui.screens.active_timer_screen.ActiveTimerScreen
import com.example.assignment1.view_models.ActiveTimerViewModel
import com.example.assignment1.ui.screens.PresetEditDestination
import com.example.assignment1.ui.screens.PresetEditScreen
import com.example.assignment1.ui.screens.PresetsDestination
import com.example.assignment1.ui.screens.PresetsScreen
import com.example.assignment1.ui.screens.SettingsDestination
import com.example.assignment1.ui.screens.SettingsScreen
import com.example.assignment1.ui.screens.UnlockableStoreDestination
import com.example.assignment1.ui.screens.UnlockableStoreScreen

/**
 * The application's mavhost, describing and facilitating navigation
 * Every navigation destination (every distinct screen) is listed here
 *
 * @param navController - the navigation controller, tracks navigation state
 * @param modifier - standard modifier-object
 */
@Composable
fun PomodoroNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    timerViewModel: ActiveTimerViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ActivePresetDestination.routeWithArgs,
        modifier = modifier
    ) {
        /**
         * the navigateBack-params provide a callback for the back-button on the various screens
         * in our case it tells the navController to return to the previous item on the navstack
         */
        composable(
            route = ActivePresetDestination.routeWithArgs,
            arguments = listOf(navArgument("presetId") {
                defaultValue = -1
                type = NavType.IntType
            })
        ) { backStackEntry ->
            ActiveTimerScreen(
                navigateBack = { navController.popBackStack() },
                viewModel = timerViewModel,
                navController = navController,
                presetID = backStackEntry.arguments?.getInt("presetId")?:0
            )
        }
        composable(
            route = PresetEditDestination.routeWithArgs,
            arguments = listOf(navArgument("presetId") {type = NavType.IntType})
        ) {
            PresetEditScreen(
                navigateBack = { navController.popBackStack() },
                navController = navController,
                presetId = it.arguments?.getInt("presetId")?:0,
            )
        }
        composable(route = PresetsDestination.route) {
            PresetsScreen(
                navigateToPresetEdit = { navController.navigate("${PresetEditDestination.route}/${it}")},
                navigateToActivePreset = {navController.navigate("${ActivePresetDestination.route}/${it}")},
                navigateBack = { navController.popBackStack() },
                navController = navController
            )
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen(
                navigateBack = { navController.popBackStack() },
                navController = navController,
                viewModel = viewModel(factory = AppViewModelProvider.Factory)
                )
        }
        composable(route = UnlockableStoreDestination.route) {
            UnlockableStoreScreen(
                navigateBack = { navController.popBackStack() },
                navController = navController)
        }
    }
}