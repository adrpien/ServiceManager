package com.example.servicemanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.util.Screens
import com.example.feature_home_presentation.home.components.HomeScreen
import com.example.servicemanager.feature_inspections_presentation.inspection_details.components.InspectionDetailsScreen
import com.example.servicemanager.feature_inspections_presentation.inspection_list.components.InspectionListScreen
import com.example.servicemanager.future_repairs_presentation.repair_details.components.RepairDetailsScreen
import com.example.servicemanager.future_repairs_presentation.repair_list.components.RepairListScreen

@Composable
fun NavigationContent(navHostController: NavHostController) {
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
        composable(
            route = Screens.RepairListScreen.route,
        ){
            RepairListScreen(
                navHostController = navHostController
            )
        }
        composable(
            route = Screens.RepairDetailsScreen.route + "/{repairId}",
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
            route = Screens.HomeScreen.route
        ){
            HomeScreen(
                navHostController = navHostController
            )
        }
    }
}