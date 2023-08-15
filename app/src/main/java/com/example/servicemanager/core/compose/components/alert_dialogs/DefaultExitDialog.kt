package com.example.servicemanager.core.compose.components.alert_dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties
import com.example.servicemanager.core.compose.components.SignatureArea
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.ui.theme.TiemedLightBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige
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