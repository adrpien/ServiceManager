package com.example.core_ui.components.menu

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core.util.UiText

data class MenuItemState(
    val icon: ImageVector,
    val text: UiText,
    val onClick: () -> (Unit)
)
