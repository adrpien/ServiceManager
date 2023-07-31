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

    val repairingDateDialogState = rememberMaterialDialogState()
    val repairingDateState = remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedRepairingDate = remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern(
                    "dd/MM/yyyy"
                )
                .format(repairingDateState.value)
        }
    }

    val openingDateDialogState = rememberMaterialDialogState()
    val openingDateState = remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedOpeningDate = remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern(
                    "dd/MM/yyyy"
                )
                .format(repairingDateState.value)
        }
    }

    val returningDateDialogState = rememberMaterialDialogState()
    val returningDateState = remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedReturningDate = remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern(
                    "dd/MM/yyyy"
                )
                .format(returningDateState.value)
        }
    }

    val signatureDialogState = rememberMaterialDialogState()
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    val deviceName = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Name",
                inspection =  repairDetailsState.value.repair.deviceName
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Manufacturer",
                inspection =  repairDetailsState.value.repair.deviceManufacturer
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Model",
                inspection =  repairDetailsState.value.repair.deviceModel
            )
        )
    }
    val deviceSn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Serial number",
                inspection =  repairDetailsState.value.repair.deviceSn
            )
        )
    }
    val deviceIn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Inventory number",
                inspection =  repairDetailsState.value.repair.deviceIn
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Ward",
                inspection =  repairDetailsState.value.repair.ward
            )
        )
    }
    val comment = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Comment",
                inspection =  repairDetailsState.value.repair.comment
            )
        )
    }
    val recipient = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Recipient",
                inspection =  repairDetailsState.value.repair.recipient
            )
        )
    }
    val defectDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Defect description",
                inspection =  repairDetailsState.value.repair.defectDescription
            )
        )
    }
    val repairDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Repair description",
                inspection =  repairDetailsState.value.repair.repairDescription
            )
        )
    }
    val partDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Part description",
                inspection =  repairDetailsState.value.repair.partDescription
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
                        inspection = event.text.deviceName
                    )
                    deviceManufacturer.value = deviceManufacturer.value.copy(
                        inspection = event.text.deviceManufacturer
                    )
                    deviceModel.value = deviceModel.value.copy(
                        inspection = event.text.deviceModel
                    )
                    ward.value = ward.value.copy(
                        inspection = event.text.ward
                    )
                    comment.value = comment.value.copy(
                        inspection = event.text.comment
                    )
                    deviceSn.value = deviceSn.value.copy(
                            inspection = event.text.deviceSn
                            )
                    deviceIn.value = deviceIn.value.copy(
                            inspection = event.text.deviceIn
                            )
                    recipient.value = recipient.value.copy(
                        inspection = event.text.recipient
                    )
                    defectDescription.value = defectDescription.value.copy(
                        inspection = event.text.defectDescription
                    )
                    repairDescription.value = repairDescription.value.copy(
                        inspection = event.text.repairDescription
                    )
                    partDescription.value = partDescription.value.copy(
                        inspection = event.text.partDescription
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

                    if(repairDetailsState.value.repair.repairId != "0") {
                        viewModel.onEvent(RepairDetailsEvent.UpdateRepair)
                    } else {
                        viewModel.onEvent(RepairDetailsEvent.SaveRepair)
                    }
                    navHostController.navigate(Screen.RepairListScreen.route)
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
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { openingDateDialogState.show()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TiemedVeryLightBeige,
                    contentColor = TiemedLightBlue
                ),
                border = BorderStroke(2.dp, TiemedLightBlue)
            ) {
                Text(
                    text = "Opening date: " + formattedOpeningDate.value.toString()
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pickup technician:",
                    color = TiemedLightBlue
                )
                TechnicianSelectionSection(
                    technicianList = technicianList,
                    technician = technicianList.find { (it.technicianId == repairDetailsState.value.repair.pickupTechnicianId) } ?: Technician(),
                    onTechnicianChange = {
                        viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.copy(
                            repair = repairDetailsState.value.repair.copy(
                                pickupTechnicianId = it.technicianId
                            )
                        ).repair
                        )
                        )
                    }
                )
            }
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
                    deviceName.value= deviceName.value.copy(inspection = name)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceName = name)))
                },
                state = deviceName)
            DefaultTextField(
                onValueChanged =  {manufacturer ->
                    deviceManufacturer.value= deviceManufacturer.value.copy(inspection = manufacturer)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceManufacturer = manufacturer)))
                },
                state = deviceManufacturer)
            DefaultTextField(
                onValueChanged =  { model ->
                    deviceModel.value= deviceModel.value.copy(inspection = model)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceModel = model)))
                },
                state = deviceModel)
            DefaultTextField(
                onValueChanged =  { serialNumber ->
                    deviceSn.value= deviceSn.value.copy(inspection = serialNumber)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceSn = serialNumber)))
                },
                state = deviceSn
            )
            DefaultTextField(
                onValueChanged =  { inventoryNumber ->
                    deviceIn.value= deviceIn.value.copy(inspection = inventoryNumber)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(deviceIn = inventoryNumber)))
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
                    ward.value= ward.value.copy(inspection = string)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(ward = string)))
                },
                state = ward)
            DefaultTextField(
                onValueChanged =  {string ->
                    comment.value= comment.value.copy(inspection = string)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(comment = string)))
                },
                state = comment)
            Text(
                text = "Repair",
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
                onValueChanged =  { defect ->
                    defectDescription.value= defectDescription.value.copy(inspection = defect)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(defectDescription = defect)))
                },
                state = defectDescription
            )
            DefaultTextField(
                onValueChanged =  { repair ->
                    repairDescription.value= repairDescription.value.copy(inspection = repair)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(repairDescription = repair)))
                },
                state = repairDescription
            )
            DefaultTextField(
                onValueChanged =  { part ->
                    partDescription.value= partDescription.value.copy(inspection = part)
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(partDescription = part)))
                },
                state = partDescription
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Repairing technician:",
                    color = TiemedLightBlue
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
                onClick = { repairingDateDialogState.show()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TiemedVeryLightBeige,
                    contentColor = TiemedLightBlue
                ),
                border = BorderStroke(2.dp, TiemedLightBlue)
            ) {
                Text(
                    text = "Repairing date: " + formattedRepairingDate.value.toString()
                )
            }
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
                    color = TiemedLightBlue
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
                    text = "Return technician:",
                    color = TiemedLightBlue
                )
                TechnicianSelectionSection(
                    technicianList = technicianList,
                    technician = technicianList.find { (it.technicianId == repairDetailsState.value.repair.returnTechnicianId) } ?: Technician(),
                    onTechnicianChange = {
                        viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.copy(
                            repair = repairDetailsState.value.repair.copy(
                                returnTechnicianId = it.technicianId
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
                onClick = { returningDateDialogState.show()},
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TiemedVeryLightBeige,
                    contentColor = TiemedLightBlue
                ),
                border = BorderStroke(2.dp, TiemedLightBlue)
            ) {
                Text(
                    text = "Returning date: " + formattedRepairingDate.value.toString()
                )
            }
            DefaultTextField(
                onValueChanged =  { string ->
                    recipient.value= recipient.value.copy(inspection = string)
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
                    contentColor = TiemedLightBlue
                ),
                border = BorderStroke(2.dp, TiemedLightBlue)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = repairDetailsState.value.signature.asImageBitmap(),
                    contentDescription = "Signature")
            }
            MaterialDialog(
                dialogState = repairingDateDialogState,
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
                    repairingDateState.value = date
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(repairingDate = date.toEpochDay().toString())))
                }
            }
            MaterialDialog(
                dialogState = openingDateDialogState,
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
                    title = "Opening date",
                    allowedDateValidator = { localDate ->
                        localDate < LocalDate.now()
                    }
                ) { date ->
                    openingDateState.value = date
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(openingDate = date.toEpochDay().toString())))
                }
            }
            MaterialDialog(
                dialogState = openingDateDialogState,
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
                    title = "Opening date",
                    allowedDateValidator = { localDate ->
                        localDate < LocalDate.now()
                    }
                ) { date ->
                    openingDateState.value = date
                    viewModel.onEvent(RepairDetailsEvent.UpdateState(repairDetailsState.value.repair.copy(openingDate = date.toEpochDay().toString())))
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
                        viewModel.onEvent(RepairDetailsEvent.UpdateSignatureState(bitmap))
                    }
                }
            }
        }
    }
}

