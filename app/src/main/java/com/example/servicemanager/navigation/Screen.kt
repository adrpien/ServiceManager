package com.example.servicemanager.navigation

import com.example.servicemanager.core.util.Constans
import com.example.servicemanager.navigation.Screen.HomeScreen.route

sealed class Screen(val route: String){
    object HomeScreen: Screen(Constans.ROUTE_HOME_SCREEN)
    object InspectionListScreen: Screen(Constans.ROUTE_INSPECTION_LIST_SCREEN)
    object InspectionDetailsScreen: Screen(Constans.ROUTE_INSPECTION_DETAILS_SCREEN)
    object RepairDetailsScreen: Screen(Constans.ROUTE_REPAIR_DETAILS_SCREEN)
    object RepairListScreen: Screen(Constans.ROUTE_REPAIR_LIST_SCREEN)
    object UserLoginScreen: Screen(Constans.ROUTE_USER_LOGIN_SCREEN)
    object ContentComposable: Screen(Constans.ROUTE_CONTENT_COMPOSABLE)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg" )
            }
        }
    }
}


