package com.example.core.util


sealed class Screen(val route: String){
    object InspectionStateListManagerScreen: Screen(NavigationRoutes.ROUTE_INSPECTION_STATE_LIST_MANAGER_SCREEN)
    object RepairStateListManagerScreen: Screen(NavigationRoutes.ROUTE_REPAIR_STATE_LIST_MANAGER_SCREEN)
    object HomeScreen: Screen(NavigationRoutes.ROUTE_HOME_SCREEN)
    object InspectionListScreen: Screen(NavigationRoutes.ROUTE_INSPECTION_LIST_SCREEN)
    object InspectionDetailsScreen: Screen(NavigationRoutes.ROUTE_INSPECTION_DETAILS_SCREEN)
    object RepairDetailsScreen: Screen(NavigationRoutes.ROUTE_REPAIR_DETAILS_SCREEN)
    object RepairListScreen: Screen(NavigationRoutes.ROUTE_REPAIR_LIST_SCREEN)
    object UserLoginScreen: Screen(NavigationRoutes.ROUTE_USER_LOGIN_SCREEN)
    object ContentComposable: Screen(NavigationRoutes.ROUTE_CONTENT_COMPOSABLE)
    object ContentWithNavigationComposable: Screen(NavigationRoutes.ROUTE_CONTENT_WITH_NAVIGATION_COMPOSABLE)

    object AppSettingsScreen: Screen(NavigationRoutes.APP_SETTINGS_SCREEN)
    object DatabaseSettingsScreen: Screen(NavigationRoutes.DATABASE_SETTINGS_SCREEN)
    object HospitalListManagerScreen: Screen(NavigationRoutes.ROUTE_HOSPITAL_LIST_MANAGER_SCREEN)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg" )
            }
        }
    }
}


