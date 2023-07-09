package com.example.servicemanager.navigation

import com.example.servicemanager.navigation.Screen.HomeScreen.route

sealed class Screen(val route: String){
    object HomeScreen: Screen("home")
    object InspectionListScreen: Screen("inspection_list")
    object InspectionDetailsScreen: Screen("inspection_details")
    object RepairScreen: Screen("repair_details")
    object RepairsScreen: Screen("repair_list")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg" )
            }
        }
    }
}


