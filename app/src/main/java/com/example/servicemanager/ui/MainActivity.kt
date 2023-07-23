package com.example.servicemanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.servicemanager.navigation.BottomNavigationBar
import com.example.servicemanager.navigation.BottomNavigationItem
import com.example.servicemanager.navigation.Navigation
import com.example.servicemanager.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        itemList = listOf(
                            BottomNavigationItem(
                                name = "Inspections",
                                route = Screen.InspectionListScreen.route,
                                icon = Icons.Default.Checklist,
                                badgeCount = 0
                            ),
                            BottomNavigationItem(
                                name = "Repairs",
                                route = Screen.RepairListScreen.route,
                                icon = Icons.Default.Settings,
                                badgeCount = 0
                            ),
                            BottomNavigationItem(
                                name = "Home",
                                route = Screen.HomeScreen.route,
                                icon = Icons.Default.VerifiedUser,
                                badgeCount = 0
                            )

                        ),
                        navHostController = navHostController,
                        onItemClick = {
                            navHostController.navigate(it.route)
                        })
                 }
            ) {
                Column(modifier = Modifier.padding(it)) {
                    Navigation(
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}
