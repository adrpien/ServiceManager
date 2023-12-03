package com.example.servicemanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.util.Screens
import com.example.servicemanager.feature_authentication_presentation.login.components.LoginScreen

@Composable
fun LoginNavigation() {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Screens.UserLoginScreen.route)
    {
        composable(
            route = Screens.UserLoginScreen.route
        ) {
            LoginScreen(navHostController = navHostController)
        }
        composable(
            route = Screens.ContentWithNavigationComposable.route  + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ) {
            MainScreenNavigation(
                userId = it.arguments?.getString("userId") ?: "0"

            )
        }
    }
}