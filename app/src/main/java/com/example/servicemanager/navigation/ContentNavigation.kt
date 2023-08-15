package com.example.servicemanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.servicemanager.feature_inspections.presentation.inspection_details.components.InspectionDetailsScreen
import com.example.servicemanager.feature_inspections.presentation.inspection_list.components.InspectionListScreen
import com.example.servicemanager.feature_repairs.presentation.repair_details.components.RepairDetailsScreen
import com.example.servicemanager.feature_repairs.presentation.repair_list.components.RepairListScreen

@Composable
fun ContentNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.RepairListScreen.route) {
        composable(
            route = Screen.InspectionListScreen.route,
        ){
            InspectionListScreen(
                navHostController = navHostController
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
            route = Screen.RepairListScreen.route,
        ){
            RepairListScreen(
                navHostController = navHostController
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
    }
}