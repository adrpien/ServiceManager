package com.example.servicemanager.core.compose

data class DefaultTextFieldState(
    val isHintVisible: Boolean = true,
    val hint: String = "",
    val text: String = ""
) {
}