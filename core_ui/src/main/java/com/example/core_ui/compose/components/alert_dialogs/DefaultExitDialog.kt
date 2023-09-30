package com.example.core_ui.compose.components.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties

@Composable
fun DefaultExitDialog(
    dialogState: MaterialDialogState
) {

    MaterialDialog(
        dialogState = dialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = TiemedVeryLightBeige,
        buttons = {
            positiveButton(
                text = "Confirm",
                textStyle = TextStyle(color = TiemedLightBlue)
            ) {}
            negativeButton(
                text = "Cancel",
                textStyle = TextStyle(color = TiemedLightBlue)
            )
        }
    ) {
        customView {

        }
    }
}