package com.example.core.util


sealed class Screens(val route: String){
    object HomeScreen: Screens(Constans.ROUTE_HOME_SCREEN)
    object InspectionListScreen: Screens(Constans.ROUTE_INSPECTION_LIST_SCREEN)
    object InspectionDetailsScreen: Screens(Constans.ROUTE_INSPECTION_DETAILS_SCREEN)
    object RepairDetailsScreen: Screens(Constans.ROUTE_REPAIR_DETAILS_SCREEN)
    object RepairListScreen: Screens(Constans.ROUTE_REPAIR_LIST_SCREEN)
    object UserLoginScreen: Screens(Constans.ROUTE_USER_LOGIN_SCREEN)
    object ContentComposable: Screens(Constans.ROUTE_CONTENT_COMPOSABLE)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg" )
            }
        }
    }
}


