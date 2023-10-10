package com.example.feature_app_presentation.components.other

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.example.core.theme.TiemedLightBeige
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DefaultDatePickerDialog(
    dialogState: MaterialDialogState,
    onClick: (LocalDate) -> Unit,
    initialDate: LocalDate = LocalDate.now(),
    title: String,

) {

    MaterialDialog(
        dialogState = dialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = TiemedLightBeige,
        buttons = {
            button(
                text = "Today",
                onClick = {
                    // TODO....
                }
            )
            positiveButton(
                text = "Confirm")
            negativeButton(
                text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = initialDate,
            title = title,
            allowedDateValidator = { localDate ->
                localDate < LocalDate.now()
            },
        ){
            onClick(it)
        }
    }

}