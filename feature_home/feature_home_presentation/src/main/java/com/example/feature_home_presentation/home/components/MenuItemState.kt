package com.example.feature_home_presentation.home.components

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core.util.UiText

data class MenuItemState(
    val icon: ImageVector,
    val text: UiText,
    val onClick: () -> (Unit)
)
