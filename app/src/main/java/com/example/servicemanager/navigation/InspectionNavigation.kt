package com.example.servicemanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.util.Screens
import com.example.servicemanager.feature_inspections_presentation.inspection_details.components.InspectionDetailsScreen
import com.example.servicemanager.feature_inspections_presentation.inspection_list.components.InspectionListScreen

@Composable
fun InspectionNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.RepairListScreen.route) {
        composable(
            route = Screens.InspectionListScreen.route,
        ){
            InspectionListScreen(
                navHostController = navHostController
            )
        }
        composable(
            route = Screens.InspectionDetailsScreen.route + "/{inspectionId}",
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
    }
}