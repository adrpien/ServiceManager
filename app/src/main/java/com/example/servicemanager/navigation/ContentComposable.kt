package com.example.servicemanager.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.servicemanager.navigation.*

@Composable
fun ContentComposable(
) {
    val navHostController = rememberNavController()

    var showDialog = remember { mutableStateOf(false) }

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
            ContentNavigation(
                navHostController = navHostController
            )
        }
    }
}