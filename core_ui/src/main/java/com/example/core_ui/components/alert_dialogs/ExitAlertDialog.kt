package com.example.core_ui.components.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*

@Composable
fun ExitAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    contentText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit
) {
        AlertDialog(
            shape = MaterialTheme.shapes.medium,
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
            textContentColor = MaterialTheme.colorScheme.onSecondary,
            onDismissRequest = onDismissRequest ,
            title = { Text(text = title) },
            text = { Text(text = contentText) },
            confirmButton = {
                Button(
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = { onConfirm() },
                ) {
                    Text(
                        text = "Confirm",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                ) {
                    Text(
                        text = "Dissmiss",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        )
}