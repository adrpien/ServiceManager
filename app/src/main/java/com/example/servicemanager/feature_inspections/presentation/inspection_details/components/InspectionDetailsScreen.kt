package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.core.util.DefaultTextFieldState
import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun InspectionDetailsScreen(
    inspectionId: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: InspectionDetailsViewModel = hiltViewModel(),
) {

    val inspectionDetailsState = viewModel.inspectionDetailsState

    val inspectionDateState = remember {
        mutableStateOf(LocalDate.now())
    }

    val formattedInspectionDate = remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern(
                    "dd/MM/yyyy"
                )
                .format(inspectionDateState.value)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    val deviceName = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Name",
                text =  inspectionDetailsState.value.inspection.deviceName
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Manufacturer",
                text =  inspectionDetailsState.value.inspection.deviceManufacturer
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Model",
                text =  inspectionDetailsState.value.inspection.deviceModel
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Ward",
                text =  inspectionDetailsState.value.inspection.ward
            )
        )
    }

    val hospitalList = viewModel.inspectionDetailsState.value.hospitalList

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.UpdateInspection -> {
                    deviceName.value = deviceName.value.copy(
                        text = event.text.deviceName
                    )
                    deviceManufacturer.value = deviceManufacturer.value.copy(
                        text = event.text.deviceManufacturer
                    )
                    deviceModel.value = deviceModel.value.copy(
                        text = event.text.deviceModel
                    )
                    ward.value = ward.value.copy(
                        text = event.text.ward
                    )
                }
                is UiEvent.ShowSnackBar -> {
                    //
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            Text(
                text = "Device",
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
            DefaultTextField(
                onValueChanged =  {
                    deviceName.value= deviceName.value.copy(text = it)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceName = it)))
                },
                state = deviceName)
            DefaultTextField(
                onValueChanged =  {
                    deviceManufacturer.value= deviceManufacturer.value.copy(text = it)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceManufacturer = it)))
                },
                state = deviceManufacturer)
            DefaultTextField(
                onValueChanged =  {
                    deviceModel.value= deviceModel.value.copy(text = it)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceModel = it)))
                },
                state = deviceModel)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Localization",
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
            HospitalFilterSection(
                hospitalList = hospitalList,
                hospital = hospitalList.find { (it.hospitalId == inspectionDetailsState.value.inspection.hospitalId ) } ?: Hospital(),
                onHospitalChange = {
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.copy(
                        inspection = inspectionDetailsState.value.inspection.copy(
                            hospitalId = it.hospitalId
                        )
                    ).inspection
                    )
                    )
                }
            )
            DefaultTextField(
                onValueChanged =  {
                    ward.value= ward.value.copy(text = it)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(ward = it)))
                },
                state = ward)
            //DefaultDateButton()
            Button(
                onClick = { dateDialogState.show() }
            ) {
                Text(
                    text = "Inspection date: " + formattedInspectionDate.value.toString()
                )
            }
            MaterialDialog(
                dialogState = dateDialogState,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                backgroundColor = Color.LightGray,
                buttons = {
                    positiveButton("Confirm")
                    negativeButton("Cancel")
                }
            ) {
                datepicker(
                    initialDate = Instant.ofEpochMilli(inspectionDetailsState.value.inspection.inspectionDate.toLong()).atZone(
                        ZoneId.systemDefault()).toLocalDate(),
                    title = "Inspection date",
                    allowedDateValidator = { localDate ->
                        localDate < LocalDate.now()
                    }
                ) { date ->
                    inspectionDateState.value = date
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(inspectionDate = date.toEpochDay().toString())))
                }
            }
        }
    }
}

