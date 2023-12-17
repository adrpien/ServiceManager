package com.example.core_ui.components.menu

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core.util.Screen


data class BottomNavigationItem(
    val name: String,
    val route: Screen,
    val icon: ImageVector,
    val badgeCount: Int = 0
) {
}