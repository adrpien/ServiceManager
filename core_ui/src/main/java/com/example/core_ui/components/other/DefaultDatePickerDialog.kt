package com.example.core_ui.components.other

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties
import com.example.core.theme.MediumBeige
import com.example.core_ui.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
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
        backgroundColor = MaterialTheme.colorScheme.secondary,
        buttons = {
            button(
                text = stringResource(R.string.today),
                onClick = {
                    // TODO DefaultDatePickerDialog "Today" onClick
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )
            positiveButton(
                text = stringResource(R.string.confirm),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                )
                )
            negativeButton(
                text = stringResource(R.string.cancel),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                )
                )
        }
    ) {
        datepicker(
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.secondary,
                headerTextColor = MaterialTheme.colorScheme.onSecondary,
                calendarHeaderTextColor = MaterialTheme.colorScheme.onSecondary,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.secondary,
                dateActiveTextColor = MaterialTheme.colorScheme.onSecondary,
                dateInactiveTextColor = MaterialTheme.colorScheme.primary,
                dateInactiveBackgroundColor = MaterialTheme.colorScheme.onSecondary
            ),
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