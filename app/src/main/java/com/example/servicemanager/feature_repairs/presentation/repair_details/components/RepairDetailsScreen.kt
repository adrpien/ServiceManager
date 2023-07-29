package com.example.servicemanager.feature_repairs.presentation.repair_details.components

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
import com.example.servicemanager.feature_app.domain.model.RepairState
import com.example.servicemanager.feature_app.domain.model.Technician
import com.example.servicemanager.feature_repairs.presentation.repair_details.RepairDetailsEvent
import com.example.servicemanager.feature_repairs.presentation.repair_details.RepairDetailsViewModel
import com.example.servicemanager.feature_repairs.presentation.repair_details.RepairDetailsViewModel.*
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
fun RepairDetailsScreen(
    repairId: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: RepairDetailsViewModel = hiltViewModel(),
) {

    val repairDetailsState = viewModel.repairDetailsState
    val repairDateState = remember {
        mutableStateOf(LocalDate.now())
    }

    val formattedRepairDate = remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern(
                    "dd/MM/yyyy"
                )
                .format(repairDateState.value)
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
                text =  repairDetailsState.value.repair.deviceName
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Manufacturer",
                text =  repairDetailsState.value.repair.deviceManufacturer
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Model",
                text =  repairDetailsState.value.repair.deviceModel
            )
        )
    }
    val deviceSn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Serial number",
                text =  repairDetailsState.value.repair.deviceSn
            )
        )
    }
    val deviceIn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Inventory number",
                text =  repairDetailsState.value.repair.deviceIn
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Ward",
                text =  repairDetailsState.value.repair.ward
            )
        )
    }
    val comment = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Comment",
                text =  repairDetailsState.value.repair.comment
            )
        )
    }
    val recipient = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Recipient",
                text =  repairDetailsState.value.repair.recipient
            )
        )
    }
    val defectDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Defect description",
                text =  repairDetailsState.value.repair.defectDescription
            )
        )
    }
    val repairDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Repair description",
                text =  repairDetailsState.value.repair.repairDescription
            )
        )
    }
    val partDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Part description",
                text =  repairDetailsState.value.repair.partDescription
            )
        )
    }

    val hospitalList = viewModel.repairDetailsState.value.hospitalList
    val estStateList = viewModel.repairDetailsState.value.estStateList
    val repairStateList = viewModel.repairDetailsState.value.repairStateList
    val technicianList = viewModel.repairDetailsState.value.technicianList


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
                    recipient.value = recipient.value.copy(
                        text = event.text.recipient
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
                    // TODO Save/Update Repair Small bug here to repair, when creating new record and saving signature at the same time, signature is saved as "0.jpg"
                    viewModel.onEvent(RepairDetailsEvent.UpdateRepair)
                    viewModel.onEvent(RepairDetailsEvent.SaveSignature)
                    navHostController.navigate(Screen.RepairListScreen.route)
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
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceName = name)))
                },
                state = deviceName)
            DefaultTextField(
                onValueChanged =  {manufacturer ->
                    deviceManufacturer.value= deviceManufacturer.value.copy(text = manufacturer)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceManufacturer = manufacturer)))
                },
                state = deviceManufacturer)
            DefaultTextField(
                onValueChanged =  { model ->
                    deviceModel.value= deviceModel.value.copy(text = model)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceModel = model)))
                },
                state = deviceModel)
            DefaultTextField(
                onValueChanged =  { serialNumber ->
                    deviceSn.value= deviceSn.value.copy(text = serialNumber)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceSn = serialNumber)))
                },
                state = deviceSn
            )
            DefaultTextField(
                onValueChanged =  { inventoryNumber ->
                    deviceIn.value= deviceIn.value.copy(text = inventoryNumber)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceIn = inventoryNumber)))
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
            HospitalSelectionSection(
                hospitalList = hospitalList,
                hospital = hospitalList.find { (it.hospitalId == repairDetailsState.value.repair.hospitalId ) } ?: Hospital(),
                onHospitalChange = {
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.copy(
                        repair = repairDetailsState.value.repair.copy(
                            hospitalId = it.hospitalId
                        )
                    ).repair
                    )
                    )
                }
            )
            DefaultTextField(
                onValueChanged =  {string ->
                    ward.value= ward.value.copy(text = string)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(ward = string)))
                },
                state = ward)
            DefaultTextField(
                onValueChanged =  {string ->
                    comment.value= comment.value.copy(text = string)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(comment = string)))
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EstState:",
                    color = TiemedMediumBlue
                    )

                EstStateSelectionSection(
                    estStateList = estStateList,
                    estState = estStateList.find { (it.estStateId == repairDetailsState.value.repair.estStateId ) } ?: EstState(),
                    onEstStateChange = {
                        viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.copy(
                            repair = repairDetailsState.value.repair.copy(
                                estStateId = it.estStateId
                            )
                        ).repair
                        )
                        )
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RepairState:",
                    color = TiemedMediumBlue
                )
                RepairStateSelectionSection(
                    repairStateList = repairStateList,
                    repairState = repairStateList.find { (it.repairStateId == repairDetailsState.value.repair.repairStateId ) } ?: RepairState(),
                    onRepairStateChange = {
                        viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.copy(
                            repair = repairDetailsState.value.repair.copy(
                                repairStateId = it.repairStateId
                            )
                        ).repair
                        )
                        )
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Repairing technician:",
                    color = TiemedMediumBlue
                )
                TechnicianSelectionSection(
                    technicianList = technicianList,
                    technician = technicianList.find { (it.technicianId == repairDetailsState.value.repair.repairTechnicianId) } ?: Technician(),
                    onTechnicianChange = {
                        viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.copy(
                            repair = repairDetailsState.value.repair.copy(
                                repairTechnicianId = it.technicianId
                            )
                        ).repair
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
                    contentColor = TiemedMediumBlue
                ),
                border = BorderStroke(2.dp, TiemedMediumBlue)
            ) {
                Text(
                    text = "Repair date: " + formattedRepairDate.value.toString()
                )
            }
            DefaultTextField(
                onValueChanged =  { string ->
                    recipient.value= recipient.value.copy(text = string)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(recipient = string)))
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
                    bitmap = repairDetailsState.value.signature.asImageBitmap(),
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
                    initialDate = Instant.ofEpochMilli(repairDetailsState.value.repair.repairingDate.toLong()).atZone(
                        ZoneId.systemDefault()).toLocalDate(),
                    title = "Repairing date",
                    allowedDateValidator = { localDate ->
                        localDate < LocalDate.now()
                    }
                ) { date ->
                    repairDateState.value = date
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(repairingDate = date.toEpochDay().toString())))
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
                        viewModel.onEvent(RepairDetailsEvent.UpdateSignatureState(bitmap))
                    }
                }
            }
        }
    }
}

