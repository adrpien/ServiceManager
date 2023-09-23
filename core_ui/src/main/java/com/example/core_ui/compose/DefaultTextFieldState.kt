package com.example.core_ui.compose

data class DefaultTextFieldState(
    val isHintVisible: Boolean = true,
    val hint: String = "",
    val value: String = "",
    val clickable: Boolean = true
) {
}