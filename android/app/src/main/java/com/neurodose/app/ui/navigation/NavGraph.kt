package com.neurodose.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neurodose.app.ui.screen.HomeScreen
import com.neurodose.app.ui.screen.LogIntakeScreen
import com.neurodose.app.ui.screen.VisualizationScreen
import com.neurodose.app.ui.screen.HistoryScreen
import com.neurodose.app.ui.screen.SettingsScreen

/**
 * Navigation routes for the NeuroDose app.
 * 
 * These constants define the routes used in the navigation graph.
 * Each route corresponds to a screen in the app.
 */
object NavRoutes {
    const val HOME = "home"
    const val LOG_INTAKE = "log_intake"
    const val VISUALIZATION = "visualization"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
}

/**
 * NeuroDoseNavGraph defines the navigation structure for the app.
 * 
 * Jetpack Navigation Compose provides a type-safe way to navigate
 * between screens. The NavHost composable manages the navigation
 * and displays the appropriate screen based on the current route.
 * 
 * @param navController The navigation controller
 */
@Composable
fun NeuroDoseNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        // Home screen - main entry point
        composable(NavRoutes.HOME) {
            HomeScreen()
        }
        
        // Log intake screen - for adding new intakes
        composable(NavRoutes.LOG_INTAKE) {
            LogIntakeScreen()
        }
        
        // Visualization screen - for viewing concentration curves
        composable(NavRoutes.VISUALIZATION) {
            VisualizationScreen()
        }
        
        // History screen - for viewing past intakes
        composable(NavRoutes.HISTORY) {
            HistoryScreen()
        }
        
        // Settings screen - for app configuration
        composable(NavRoutes.SETTINGS) {
            SettingsScreen()
        }
    }
}

