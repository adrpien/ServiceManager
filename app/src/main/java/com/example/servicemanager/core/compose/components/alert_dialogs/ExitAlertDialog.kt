package com.example.servicemanager.core.compose.components.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.OnBackPressedCallback
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ExitAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    contentText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(

        onDismissRequest = { onDismiss() },
        title = { Text(text = title) },
        text = { Text(text = contentText) },
        confirmButton = {
            Button(
                onClick = { onConfirm() },
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Dissmiss")
            }
        }
    )
}