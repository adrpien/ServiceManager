package com.example.core_ui.components.alert_dialogs

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties
import com.example.core_ui.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate
import java.util.Locale

@Composable
fun DefaultDatePickerDialog(
    dialogState: MaterialDialogState,
    onClick: (LocalDate) -> Unit,
    initialDate: LocalDate = LocalDate.now(),
    title: String,

) {

    var selectedDate by remember { mutableStateOf(initialDate) }

    MaterialDialog(
        dialogState = dialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        buttons = {
            positiveButton(
                text = stringResource(R.string.confirm),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                ),
                onClick = {
                    onClick(selectedDate)
                }
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
                dateActiveBackgroundColor = MaterialTheme.colorScheme.primary,
                dateActiveTextColor = MaterialTheme.colorScheme.onPrimary,
                dateInactiveTextColor = MaterialTheme.colorScheme.onPrimary,
                dateInactiveBackgroundColor = MaterialTheme.colorScheme.secondary
            ),
            locale = Locale.getDefault(),
            initialDate = initialDate,
            title = title,
            waitForPositiveButton = false,
            allowedDateValidator = { localDate ->
                localDate < LocalDate.now()
            },
        ){
            selectedDate = it
        }
    }

}
