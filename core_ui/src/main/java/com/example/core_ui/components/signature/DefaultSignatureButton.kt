package com.example.core_ui.components.signature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.core.theme.MediumBeige
import com.example.core_ui.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DefaultSignatureButton(
    modifier: Modifier = Modifier,
    dateInMillis: Long,
    title: String,
    onClick: (String) -> Unit,
) {

    val repairDateState = remember {
        mutableStateOf(LocalDate.now())
    }
    val dateDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = MediumBeige,
        buttons = {
            positiveButton(
                text = stringResource(R.string.confirm)
            )
            negativeButton(
                text = stringResource(R.string.cancel)
            )
        }
    ) {
        datepicker(
            initialDate = Instant.ofEpochMilli(dateInMillis).atZone(
                ZoneId.systemDefault()).toLocalDate(),
            title = title,
            allowedDateValidator = { localDate ->
                localDate < LocalDate.now()
            }
        ) { date ->
            repairDateState.value = date
            onClick(date.toEpochDay().toString())
        }
    }
}