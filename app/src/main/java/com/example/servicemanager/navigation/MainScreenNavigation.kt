package com.example.servicemanager.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.core.util.Screen
import com.example.core_ui.components.menu.BottomNavigationItem
import com.example.servicemanager.R

@Composable
fun MainScreenNavigation(
    userId: String?
) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                itemList = listOf(
                    BottomNavigationItem(
                        name = stringResource(R.string.inspections),
                        route = Screen.InspectionListScreen,
                        icon = Icons.Default.Checklist,
                        badgeCount = 0
                    ),
                    BottomNavigationItem(
                        name = stringResource(R.string.repairs),
                        route = Screen.RepairListScreen,
                        icon = Icons.Default.Settings,
                        badgeCount = 0
                    ),
                    BottomNavigationItem(
                        name = stringResource(R.string.home),
                        route = Screen.HomeScreen,
                        icon = Icons.Default.Home,
                        badgeCount = 0
                    )

                ),
                navHostController = navHostController,
                onItemClick = {
                    navHostController.navigate(it.route.withArgs(userId ?: "0"))
                },
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            MainScreenNavigationContent(
                userId = userId,
                navHostController = navHostController,
            )
        }
    }
}