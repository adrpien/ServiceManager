package com.example.servicemanager.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.core.util.Screens

@Composable
fun MainScreenNavigation(
) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                itemList = listOf(
                    BottomNavigationItem(
                        name = "Inspections",
                        route = Screens.InspectionListScreen.route,
                        icon = Icons.Default.Checklist,
                        badgeCount = 0
                    ),
                    BottomNavigationItem(
                        name = "Repairs",
                        route = Screens.RepairListScreen.route,
                        icon = Icons.Default.Settings,
                        badgeCount = 0
                    ),
                    BottomNavigationItem(
                        name = "Home",
                        route = Screens.HomeScreen.route,
                        icon = Icons.Default.Home,
                        badgeCount = 0
                    )

                ),
                navHostController = navHostController,
                onItemClick = {
                    navHostController.navigate(it.route)
                },
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            MainScreenNavigationContent(
                navHostController = navHostController
            )
        }
    }
}