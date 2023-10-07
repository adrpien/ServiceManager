package com.example.servicemanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.util.Screen
import com.example.servicemanager.feature_authentication_presentation.login.components.LoginScreen

@Composable
fun LoginNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.UserLoginScreen.route)
    {
        composable(
            route = Screen.UserLoginScreen.route
        ) {
            LoginScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.ContentComposable.route
        ) {
            ContentComposable()
        }
    }
}