package com.example.servicemanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.util.Screen
import com.example.feature_home_presentation.app_settings.components.AppSettingsScreen
import com.example.feature_home_presentation.database_settings.components.DatabaseSettingsScreen
import com.example.feature_home_presentation.home.components.HomeScreen
import com.example.servicemanager.feature_inspections_presentation.inspection_details.components.InspectionDetailsScreen
import com.example.servicemanager.feature_inspections_presentation.inspection_list.components.InspectionListScreen
import com.example.servicemanager.future_repairs_presentation.repair_details.components.RepairDetailsScreen
import com.example.servicemanager.future_repairs_presentation.repair_list.components.RepairListScreen

@Composable
fun MainScreenNavigationContent(
    userId: String? = "0",
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.RepairListScreen.route + "/{userId}"
    ) {

        composable(
            route = Screen.InspectionListScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ){
            InspectionListScreen(
                navHostController = navHostController,
                userId = userId
            )
        }

        composable(
            route = Screen.InspectionDetailsScreen.route + "/{inspectionId}",
            arguments = listOf(
                navArgument(name = "inspectionId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }

            )
        ){
            InspectionDetailsScreen(
                navHostController = navHostController,
                inspectionId = it.arguments?.getString("inspectionId") ?: "0"
            )
        }

        composable(
            route = Screen.RepairListScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ){
            RepairListScreen(
                navHostController = navHostController,
                userId = userId
            )
        }

        composable(
            route = Screen.RepairDetailsScreen.route + "/{repairId}",
            arguments = listOf(
                navArgument(name = "repairId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ){
            RepairDetailsScreen(
                navHostController = navHostController,
                repairId = it.arguments?.getString("repairId") ?: "0"
            )
        }

        composable(
            route = Screen.HomeScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ){
            HomeScreen(
                navHostController = navHostController,
                userId = userId
            )
        }

        composable(
            route = Screen.AppSettingsScreen.route
        ) {
            AppSettingsScreen(
                navHostController = navHostController
            )
        }

        composable(
            route = Screen.DatabaseSettingsScreen.route
        ) {
            DatabaseSettingsScreen(
                navHostController = navHostController
            )
        }
    }
}