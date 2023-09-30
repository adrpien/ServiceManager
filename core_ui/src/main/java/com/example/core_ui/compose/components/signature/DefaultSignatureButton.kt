package com.example.servicemanager.core.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
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
        backgroundColor = TiemedLightBeige,
        buttons = {
            positiveButton(
                text = "Confirm")
            negativeButton(
                text = "Cancel")
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