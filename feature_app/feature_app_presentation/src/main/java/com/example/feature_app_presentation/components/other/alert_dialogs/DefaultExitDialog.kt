package com.example.feature_app_presentation.components.other.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties
import com.example.core.theme.TiemedLightBlue
import com.example.core.theme.TiemedLightBeige
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.customView

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
        backgroundColor = TiemedLightBeige,
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