package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import SignatureArea
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
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
import com.example.servicemanager.core.compose.components.DefaultTextField
import com.example.servicemanager.core.compose.components.HospitalFilterSection
import com.example.servicemanager.feature_app.domain.model.Hospital
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO Save/Update Inspection Small bug here to repair, when creating new record and saving signature at the same time, signature is saved as "0.jpg"
                    viewModel.onEvent(InspectionDetailsEvent.UpdateInspection)
                    viewModel.onEvent(InspectionDetailsEvent.SaveSignature)
                    navHostController.navigate(Screen.InspectionListScreen.route)
                },
                backgroundColor = TiemedMediumBlue
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
                color = TiemedMediumBlue,
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
                color = TiemedMediumBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = TiemedMediumBlue,
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
                color = TiemedMediumBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = TiemedMediumBlue,
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
                    backgroundColor = TiemedVeryLightBeige,
                    contentColor = TiemedMediumBlue
                ),
                border = BorderStroke(2.dp, TiemedMediumBlue)
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

                    .padding(8.dp),
                onClick = { signatureDialogState.show()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TiemedVeryLightBeige,
                    contentColor = TiemedMediumBlue
                ),
                border = BorderStroke(2.dp, TiemedMediumBlue)
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
                        textStyle = TextStyle(color = TiemedMediumBlue)
                    ){}
                    negativeButton(
                        text = "Cancel",
                        textStyle = TextStyle(color = TiemedMediumBlue)
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

