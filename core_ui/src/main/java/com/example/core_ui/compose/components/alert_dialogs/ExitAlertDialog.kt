package com.example.core_ui.compose.components.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import com.example.core.ui.theme.TiemedLightBlue
import com.example.core.ui.theme.TiemedVeryLightBeige
import com.example.core.ui.theme.TiemedVeryLightBlue

@Composable
fun ExitAlertDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    title: String,
    contentText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (isVisible){
        AlertDialog(
            shape = RectangleShape,
            containerColor = TiemedVeryLightBeige,
            titleContentColor = TiemedLightBlue,
            textContentColor = TiemedLightBlue,
            onDismissRequest = { onDismiss() },
            title = { Text(text = title) },
            text = { Text(text = contentText) },
            confirmButton = {
                Button(
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TiemedVeryLightBlue,
                        contentColor = TiemedLightBlue
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
                        containerColor = TiemedVeryLightBlue,
                        contentColor = TiemedLightBlue
                    )
                ) {
                    Text(text = "Dissmiss")
                }
            }
        )
    }

}