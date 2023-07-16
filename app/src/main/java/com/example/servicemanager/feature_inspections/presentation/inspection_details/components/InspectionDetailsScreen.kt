package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.core.util.DefaultTextFieldState
import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel.*
import com.vanpra.composematerialdialogs.MaterialDialog
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

    val scrollState = rememberScrollState()

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
    val deviceSn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Serial number",
                text =  inspectionDetailsState.value.inspection.deviceSn
            )
        )
    }
    val deviceIn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Inventory number",
                text =  inspectionDetailsState.value.inspection.deviceIn
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
    val comment = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Comment",
                text =  inspectionDetailsState.value.inspection.comment
            )
        )
    }
    val recipient = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Recipient",
                text =  inspectionDetailsState.value.inspection.recipient
            )
        )
    }

    val hospitalList = viewModel.inspectionDetailsState.value.hospitalList

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.UpdateTextFields -> {
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
                    comment.value = comment.value.copy(
                        text = event.text.comment
                    )
                    deviceSn.value = deviceSn.value.copy(
                            text = event.text.deviceSn
                            )
                    deviceIn.value = deviceIn.value.copy(
                            text = event.text.deviceIn
                            )
                    recipient.value = deviceIn.value.copy(
                        text = event.text.deviceIn
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(scrollState)) {
            Text(
                text = "Device",
                fontSize = 20.sp,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = Color.Blue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
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
            DefaultTextField(
                onValueChanged =  {
                    deviceSn.value= deviceSn.value.copy(text = it)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceSn = it)))
                },
                state = deviceSn)
            DefaultTextField(
                onValueChanged =  {
                    deviceIn.value= deviceIn.value.copy(text = it)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceIn = it)))
                },
                state = deviceIn)
            Text(
                text = "Localization",
                fontSize = 20.sp,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = Color.Blue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
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
            DefaultTextField(
                onValueChanged =  {
                    comment.value= comment.value.copy(text = it)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(comment = it)))
                },
                state = comment)
            Text(
                text = "Result",
                fontSize = 20.sp,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = Color.Blue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { dateDialogState.show()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Blue
                ),
                border = BorderStroke(2.dp, Color.Blue)
            ) {
                Text(
                    text = "Inspection date: " + formattedInspectionDate.value.toString()
                )
            }
            DefaultTextField(
                onValueChanged =  {
                    recipient.value= recipient.value.copy(text = it)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(recipient = it)))
                },
                state = recipient)
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

