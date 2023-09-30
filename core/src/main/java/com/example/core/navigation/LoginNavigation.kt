package com.example.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.servicemanager.feature_authentication.presentation.login.components.LoginScreen
import com.example.servicemanager.ui.components.ContentComposable

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