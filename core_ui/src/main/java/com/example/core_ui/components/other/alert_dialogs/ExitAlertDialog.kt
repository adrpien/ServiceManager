package com.example.core_ui.components.other.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.graphics.RectangleShape
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige
import com.example.core.theme.VeryLightBlue

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
            shape = RectangleShape,
            containerColor = LightBeige,
            titleContentColor = LightBlue,
            textContentColor = LightBlue,
            onDismissRequest = onDismissRequest ,
            title = { Text(text = title) },
            text = { Text(text = contentText) },
            confirmButton = {
                Button(
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VeryLightBlue,
                        contentColor = LightBlue
                    ),
                    onClick = { onConfirm() },
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VeryLightBlue,
                        contentColor = LightBlue
                    )
                ) {
                    Text(text = "Dissmiss")
                }
            }
        )
}