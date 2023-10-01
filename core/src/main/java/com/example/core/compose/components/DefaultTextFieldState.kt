package com.example.core.compose.components

data class DefaultTextFieldState(
    val isHintVisible: Boolean = true,
    val hint: String = "",
    val value: String = "",
    val clickable: Boolean = true
) {
}