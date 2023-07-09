package com.example.servicemanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.servicemanager.feature_inspections.presentation.inspection_details.components.InspectionDetailsScreen
import com.example.servicemanager.feature_inspections.presentation.inspection_list.components.InspectionListScreen

@Composable
fun Navigation(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.InspectionListScreen.route) {
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
            InspectionListScreen(
                navHostController = navHostController
            )
        }
    }
}