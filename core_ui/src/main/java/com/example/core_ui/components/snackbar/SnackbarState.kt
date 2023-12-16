package com.example.core_ui.components.snackbar

data class SnackbarState(
    val isSnackbarVisible: Boolean = false,
    val message: String = "",
    val actionLabel: String? = null,
    val onActionClick: (() -> (Unit))? = null
) {
}