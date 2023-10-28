package com.example.core_ui.components.other

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.example.core.theme.TiemedBeige
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

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
        backgroundColor = TiemedBeige,
        buttons = {
            button(
                text = "Today",
                onClick = {
                    // TODO DefaultDatePickerDialog onClick
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