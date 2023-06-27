package com.example.servicemanager.core.util

data class NoteTextFieldState(
    val isHintVisible: Boolean = true,
    val hint: String = "",
    val text: String = ""
) {
}