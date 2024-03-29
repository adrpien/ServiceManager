package com.example.core_ui.components.textfield

data class DefaultTextFieldState(
    val isHintVisible: Boolean = true,
    val hint: String = "",
    val value: String = "",
    val clickable: Boolean = true
)