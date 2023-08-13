package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.core.compose.DefaultTextFieldState
import com.example.servicemanager.core.compose.components.*
import com.example.servicemanager.feature_app.domain.model.EstState
import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_app.domain.model.InspectionState
import com.example.servicemanager.feature_app.domain.model.Technician
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel.*
import com.example.servicemanager.navigation.Screen
import com.example.servicemanager.ui.theme.*
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
                value =  inspectionDetailsState.value.inspection.deviceName
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Manufacturer",
                value =  inspectionDetailsState.value.inspection.deviceManufacturer
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Model",
                value =  inspectionDetailsState.value.inspection.deviceModel
            )
        )
    }
    val deviceSn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Serial number",
                value =  inspectionDetailsState.value.inspection.deviceSn
            )
        )
    }
    val deviceIn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Inventory number",
                value =  inspectionDetailsState.value.inspection.deviceIn
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Ward",
                value =  inspectionDetailsState.value.inspection.ward
            )
        )
    }
    val comment = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Comment",
                value =  inspectionDetailsState.value.inspection.comment
            )
        )
    }
    val recipient = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Recipient",
                value =  inspectionDetailsState.value.inspection.recipient
            )
        )
    }

    val hospitalList = viewModel.inspectionDetailsState.value.hospitalList
    val estStateList = viewModel.inspectionDetailsState.value.estStateList
    val inspectionStateList = viewModel.inspectionDetailsState.value.inspectionStateList
    val technicianList = viewModel.inspectionDetailsState.value.technicianList


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.UpdateTextFields -> {
                    deviceName.value = deviceName.value.copy(
                        value = event.inspection.deviceName
                    )
                    deviceManufacturer.value = deviceManufacturer.value.copy(
                        value = event.inspection.deviceManufacturer
                    )
                    deviceModel.value = deviceModel.value.copy(
                        value = event.inspection.deviceModel
                    )
                    ward.value = ward.value.copy(
                        value = event.inspection.ward
                    )
                    comment.value = comment.value.copy(
                        value = event.inspection.comment
                    )
                    deviceSn.value = deviceSn.value.copy(
                            value = event.inspection.deviceSn
                            )
                    deviceIn.value = deviceIn.value.copy(
                            value = event.inspection.deviceIn
                            )
                    recipient.value = recipient.value.copy(
                        value = event.inspection.recipient
                    )
                }
                is UiEvent.ShowSnackBar -> {
                    //
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if(inspectionDetailsState.value.inspection.inspectionId != "0") {
                        viewModel.onEvent(InspectionDetailsEvent.UpdateInspection(inspectionDetailsState.value.inspection))
                    } else {
                        viewModel.onEvent(InspectionDetailsEvent.SaveInspection(inspectionDetailsState.value.inspection))
                    }
                    navHostController.navigate(Screen.InspectionListScreen.route)
                },
                backgroundColor = TiemedLightBlue
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save",
                    modifier = Modifier,
                tint = TiemedVeryLightBeige)
            }
        },
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(TiemedVeryLightBeige)
                .padding(8.dp)
                .verticalScroll(scrollState)
                ) {
            Text(
                text = "Device",
                fontSize = 20.sp,
                color = TiemedLightBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = TiemedLightBlue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DefaultTextField(
                onValueChanged =  { name ->
                    deviceName.value= deviceName.value.copy(value = name)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceName = name)))
                },
                state = deviceName)
            DefaultTextField(
                onValueChanged =  {manufacturer ->
                    deviceManufacturer.value= deviceManufacturer.value.copy(value = manufacturer)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceManufacturer = manufacturer)))
                },
                state = deviceManufacturer)
            DefaultTextField(
                onValueChanged =  { model ->
                    deviceModel.value= deviceModel.value.copy(value = model)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceModel = model)))
                },
                state = deviceModel)
            DefaultTextField(
                onValueChanged =  { serialNumber ->
                    deviceSn.value= deviceSn.value.copy(value = serialNumber)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceSn = serialNumber)))
                },
                state = deviceSn
            )
            DefaultTextField(
                onValueChanged =  { inventoryNumber ->
                    deviceIn.value= deviceIn.value.copy(value = inventoryNumber)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(deviceIn = inventoryNumber)))
                },
                state = deviceIn)
            Text(
                text = "Localization",
                fontSize = 20.sp,
                color = TiemedLightBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = TiemedLightBlue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            HospitalSelectionSection(
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
                    ward.value= ward.value.copy(value = string)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(ward = string)))
                },
                state = ward)
            DefaultTextField(
                onValueChanged =  {string ->
                    comment.value= comment.value.copy(value = string)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(comment = string)))
                },
                state = comment)
            Text(
                text = "Result",
                fontSize = 20.sp,
                color = TiemedLightBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = TiemedLightBlue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EstState:",
                    color = TiemedLightBlue
                    )

                EstStateSelectionSection(
                    estStateList = estStateList,
                    estState = estStateList.find { (it.estStateId == inspectionDetailsState.value.inspection.estStateId ) } ?: EstState(),
                    onEstStateChange = {
                        viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.copy(
                            inspection = inspectionDetailsState.value.inspection.copy(
                                estStateId = it.estStateId
                            )
                        ).inspection
                        )
                        )
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "InspectionState:",
                    color = TiemedLightBlue
                )
                InspectionStateSelectionSection(
                    inspectionStateList = inspectionStateList,
                    inspectionState = inspectionStateList.find { (it.inspectionStateId == inspectionDetailsState.value.inspection.inspectionStateId ) } ?: InspectionState(),
                    onInspectionStateChange = {
                        viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.copy(
                            inspection = inspectionDetailsState.value.inspection.copy(
                                inspectionStateId = it.inspectionStateId
                            )
                        ).inspection
                        )
                        )
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Technician:",
                    color = TiemedLightBlue
                )
                TechnicianSelectionSection(
                    technicianList = technicianList,
                    technician = technicianList.find { (it.technicianId == inspectionDetailsState.value.inspection.technicianId) } ?: Technician(),
                    onTechnicianChange = {
                        viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.copy(
                            inspection = inspectionDetailsState.value.inspection.copy(
                                technicianId = it.technicianId
                            )
                        ).inspection
                        )
                        )
                    }
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { dateDialogState.show()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TiemedVeryLightBeige,
                    contentColor = TiemedLightBlue
                ),
                border = BorderStroke(2.dp, TiemedLightBlue)
            ) {
                Text(
                    text = "Inspection date: " + formattedInspectionDate.value.toString()
                )
            }
            DefaultTextField(
                onValueChanged =  { string ->
                    recipient.value= recipient.value.copy(value = string)
                    viewModel.onEvent(InspectionDetailsEvent.UpdateState(inspectionDetailsState.value.inspection.copy(recipient = string)))
                },
                state = recipient)
            Button(
                modifier = Modifier

                    .padding(8.dp),
                onClick = { signatureDialogState.show()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TiemedVeryLightBeige,
                    contentColor = TiemedLightBlue
                ),
                border = BorderStroke(2.dp, TiemedLightBlue)
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
                backgroundColor = TiemedLightBeige,
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
                backgroundColor = TiemedVeryLightBeige,
                buttons = {
                    positiveButton(
                        text = "Confirm",
                        textStyle = TextStyle(color = TiemedLightBlue)
                    ){}
                    negativeButton(
                        text = "Cancel",
                        textStyle = TextStyle(color = TiemedLightBlue)
                    )
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

