package com.example.servicemanager.core.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.example.servicemanager.feature_repairs.presentation.repair_details.RepairDetailsEvent
import com.example.servicemanager.ui.theme.TiemedLightBeige
import com.example.servicemanager.ui.theme.TiemedLightBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun DefaultDateButton(
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