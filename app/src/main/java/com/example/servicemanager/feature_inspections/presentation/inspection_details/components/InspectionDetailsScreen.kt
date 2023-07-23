package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.core.components.SignatureArea
import com.example.servicemanager.core.components.DefaultTextFieldState
import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel.*
import com.example.servicemanager.navigation.Screen
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.customView
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
    val signatureDialogState = rememberMaterialDialogState()
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

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

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          // TODO Save/Update Inspection
                          viewModel.onEvent(InspectionDetailsEvent.UpdateInspection)
                    navHostController.navigate(Screen.InspectionListScreen.route)
                },
                backgroundColor = Color.Blue
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save")
            }
        },
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
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
                onValueChanged =  { name ->
                    deviceName.value= deviceName.value.copy(text = name)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceName = name)))
                },
                state = deviceName)
            DefaultTextField(
                onValueChanged =  {manufacturer ->
                    deviceManufacturer.value= deviceManufacturer.value.copy(text = manufacturer)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceManufacturer = manufacturer)))
                },
                state = deviceManufacturer)
            DefaultTextField(
                onValueChanged =  { model ->
                    deviceModel.value= deviceModel.value.copy(text = model)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceModel = model)))
                },
                state = deviceModel)
            DefaultTextField(
                onValueChanged =  { serialNumber ->
                    deviceSn.value= deviceSn.value.copy(text = serialNumber)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceSn = serialNumber)))
                },
                state = deviceSn
            )
            DefaultTextField(
                onValueChanged =  { inventoryNumber ->
                    deviceIn.value= deviceIn.value.copy(text = inventoryNumber)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceIn = inventoryNumber)))
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
                onValueChanged =  {string ->
                    ward.value= ward.value.copy(text = string)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(ward = string)))
                },
                state = ward)
            DefaultTextField(
                onValueChanged =  {string ->
                    comment.value= comment.value.copy(text = string)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(comment = string)))
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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
                onValueChanged =  {string ->
                    recipient.value= recipient.value.copy(text = string)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(recipient = string)))
                },
                state = recipient)
            Button(
                modifier = Modifier
                    // .height(600.toDp.dp)
                    // .width(1200.toDp.dp)
                    .padding(8.dp),
                onClick = { signatureDialogState.show()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Blue
                ),
                border = BorderStroke(2.dp, Color.Blue)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = inspectionDetailsState.value.signature.asImageBitmap(),
                    contentDescription = "Signature")
            }
            MaterialDialog(
                dialogState = dateDialogState,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                backgroundColor = Color.LightGray,
                buttons = {
                    button(
                        text = "Refresh",
                        onClick = {
                            // TODO Refresh date
                        })
                    positiveButton(
                        text = "Confirm")
                    negativeButton(
                        text = "Cancel")
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
            MaterialDialog(
                dialogState = signatureDialogState,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                backgroundColor = Color.LightGray,
                buttons = {
                    button("Save"){
                    }
                    positiveButton("Confirm"){
                    }
                    negativeButton("Cancel")
                }
            ) {
                customView {
                    SignatureArea() { bitmap ->
                        viewModel.onEvent(InspectionDetailsEvent.UpdateSignatureState(bitmap))
                    }
                }
            }
        }
    }
}

