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
import com.example.feature_home_presentation.est_state_list_manager.EstStateListManagerScreen
import com.example.feature_home_presentation.home.components.HomeScreen
import com.example.feature_home_presentation.hospital_list_manager.HospitalListManagerScreen
import com.example.feature_home_presentation.inspection_state_list_manager.InspectionStateListManagerScreen
import com.example.feature_home_presentation.repair_state_list_manager.RepairStateListManagerScreen
import com.example.feature_home_presentation.technician_list_manager.TechnicianListManagerScreen
import com.example.feature_home_presentation.technician_list_manager.UserTypeListManagerScreen
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
            )
        }

        composable(
            route = Screen.AppSettingsScreen.route
        ) {
            AppSettingsScreen(
            )
        }

        composable(
            route = Screen.DatabaseSettingsScreen.route
        ) {
            DatabaseSettingsScreen(
                navHostController = navHostController
            )
        }

        composable(
            route = Screen.HospitalListManagerScreen.route
        ) {
            HospitalListManagerScreen(
            )
        }

        composable(
            route = Screen.EstStateListManagerScreen.route
        ) {
            EstStateListManagerScreen(
            )
        }

        composable(
            route = Screen.HospitalListManagerScreen.route
        ) {
            HospitalListManagerScreen(
            )
        }

        composable(
            route = Screen.InspectionStateListManagerScreen.route
        ) {
            InspectionStateListManagerScreen(
            )
        }

        composable(
            route = Screen.RepairStateListManagerScreen.route
        ) {
            RepairStateListManagerScreen(
            )
        }
        composable(
            route = Screen.TechnicianListManagerScreen.route
        ) {
            TechnicianListManagerScreen(
            )
        }
        composable(
            route = Screen.UserTypeListManagerScreen.route
        ) {
            UserTypeListManagerScreen()
        }
    }
}