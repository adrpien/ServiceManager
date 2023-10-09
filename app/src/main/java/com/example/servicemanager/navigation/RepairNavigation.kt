package com.example.servicemanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.util.Screens
import com.example.servicemanager.future_repairs_presentation.repair_details.components.RepairDetailsScreen
import com.example.servicemanager.future_repairs_presentation.repair_list.components.RepairListScreen

@Composable
fun RepairNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.RepairListScreen.route) {
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
            // TODO HomeScreen with some preferences, about, stats, credits
        }
    }
}