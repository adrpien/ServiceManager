package com.example.servicemanager.navigation

import com.example.servicemanager.navigation.Screen.HomeScreen.route

sealed class Screen(val route: String){
    object HomeScreen: Screen("home")
    object InspectionListScreen: Screen("inspection_list")
    object InspectionDetailsScreen: Screen("inspection_details")
    object RepairDetailsScreen: Screen("repair_details")
    object RepairListScreen: Screen("repair_list")
    object UserLoginScreen: Screen("user_login")
    object ContentComposable: Screen("content_composable")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg" )
            }
        }
    }
}


