package com.example.feature_app_presentation.components.other

data class DefaultTextFieldState(
    val isHintVisible: Boolean = true,
    val hint: String = "",
    val value: String = "",
    val clickable: Boolean = true
) {
}