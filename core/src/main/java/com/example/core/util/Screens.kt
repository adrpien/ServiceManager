package com.example.core.util


sealed class Screens(val route: String){
    object HomeScreen: Screens(NavigationRoutes.ROUTE_HOME_SCREEN)
    object InspectionListScreen: Screens(NavigationRoutes.ROUTE_INSPECTION_LIST_SCREEN)
    object InspectionDetailsScreen: Screens(NavigationRoutes.ROUTE_INSPECTION_DETAILS_SCREEN)
    object RepairDetailsScreen: Screens(NavigationRoutes.ROUTE_REPAIR_DETAILS_SCREEN)
    object RepairListScreen: Screens(NavigationRoutes.ROUTE_REPAIR_LIST_SCREEN)
    object UserLoginScreen: Screens(NavigationRoutes.ROUTE_USER_LOGIN_SCREEN)
    object ContentComposable: Screens(NavigationRoutes.ROUTE_CONTENT_COMPOSABLE)
    object AppSettingsScreen: Screens(NavigationRoutes.APP_SETTINGS_SCREEN)
    object DatabaseSettingsScreen: Screens(NavigationRoutes.DATABASE_SETTINGS_SCREEN)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg" )
            }
        }
    }
}


