package com.example.core_ui.components.other.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.graphics.RectangleShape
import com.example.core.theme.TiemedLightBlue
import com.example.core.theme.TiemedLightBeige
import com.example.core.theme.TiemedVeryLightBlue

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
            containerColor = TiemedLightBeige,
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