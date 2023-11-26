package com.example.servicemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.servicemanager.navigation.LoginNavigation
import com.example.servicemanager.navigation.MainScreenNavigation
import com.example.servicemanager.navigation.MainScreenNavigationContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity(
) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            MainScreenNavigation()
            // LoginNavigation(navHostController)
        }
    }
}
