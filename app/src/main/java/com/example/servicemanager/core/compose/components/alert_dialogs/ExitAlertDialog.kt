package com.example.servicemanager.core.compose.components.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.OnBackPressedCallback
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.servicemanager.ui.theme.TiemedLightBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige
import com.example.servicemanager.ui.theme.TiemedVeryLightBlue

@Composable
fun ExitAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    contentText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
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