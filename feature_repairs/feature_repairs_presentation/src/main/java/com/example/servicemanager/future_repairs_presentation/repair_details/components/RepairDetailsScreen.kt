package com.example.servicemanager.future_repairs_presentation.repair_details.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.example.core.theme.LightBeige
import com.example.core.util.Helper
import com.example.core.util.Screens
import com.example.core_ui.components.other.DefaultTextField
import com.example.core_ui.components.other.DefaultTextFieldState
import com.example.core_ui.components.other.alert_dialogs.ExitAlertDialog
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.future_repairs_presentation.repair_details.RepairDetailsEvent
import com.example.servicemanager.future_repairs_presentation.repair_details.RepairDetailsViewModel
import com.example.servicemanager.future_repairs_presentation.repair_details.RepairDetailsViewModel.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.customView
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import com.example.core_ui.components.other.DefaultDatePickerDialog
import com.example.core_ui.components.other.DefaultSelectionSection
import com.example.core_ui.components.signature.SignatureArea


@Composable
fun RepairDetailsScreen(
    repairId: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: RepairDetailsViewModel = hiltViewModel(),
) {

/* ********************** STATES **************************************************************** */
    val repairDetailsState = viewModel.repairDetailsState
    val hospitalList = viewModel.repairDetailsState.value.hospitalList
    val estStateList = viewModel.repairDetailsState.value.estStateList
    val repairStateList = viewModel.repairDetailsState.value.repairStateList
    val technicianList = viewModel.repairDetailsState.value.technicianList
    val isInEditMode = viewModel.repairDetailsState.value.isInEditMode

/* ********************** DIALOGS *************************************************************** */
    val repairingDateDialogState = rememberMaterialDialogState()
    val repairingDateState = remember {
        mutableStateOf(LocalDate.now())
    }

    val openingDateDialogState = rememberMaterialDialogState()
    val openingDateState = remember {
        mutableStateOf(LocalDate.now())
    }

    val returningDateDialogState = rememberMaterialDialogState()
    val returningDateState = remember {
        mutableStateOf(LocalDate.now())
    }

    val showExitDialog = remember {
        mutableStateOf(false)
    }

    val signatureDialogState = rememberMaterialDialogState()

/* ********************** OTHERS **************************************************************** */
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val deviceName = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Name",
                value = repairDetailsState.value.repair.deviceName,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Manufacturer",
                value = repairDetailsState.value.repair.deviceManufacturer,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Model",
                value = repairDetailsState.value.repair.deviceModel,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val deviceSn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Serial number",
                value = repairDetailsState.value.repair.deviceSn,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val deviceIn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Inventory number",
                value = repairDetailsState.value.repair.deviceIn,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Ward",
                value = repairDetailsState.value.repair.ward,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val comment = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Comment",
                value = repairDetailsState.value.repair.comment,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val recipient = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Recipient",
                value = repairDetailsState.value.repair.recipient,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val defectDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Defect description",
                value = repairDetailsState.value.repair.defectDescription,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val repairDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Repair description",
                value = repairDetailsState.value.repair.repairDescription,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val partDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Part description",
                value = repairDetailsState.value.repair.partDescription,
                clickable = isInEditMode
            )
        )
    }

/* ********************** BACK HANDLER  ********************************************************* */

    BackHandler(isInEditMode) {
        showExitDialog.value = true
    }

/* ********************** UI EVENTS HANDLING  *************************************************** */
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.UpdateTextFields -> {
                    deviceName.value = deviceName.value.copy(
                        value = event.text.deviceName
                    )
                    deviceManufacturer.value = deviceManufacturer.value.copy(
                        value = event.text.deviceManufacturer
                    )
                    deviceModel.value = deviceModel.value.copy(
                        value = event.text.deviceModel
                    )
                    ward.value = ward.value.copy(
                        value = event.text.ward
                    )
                    comment.value = comment.value.copy(
                        value = event.text.comment
                    )
                    deviceSn.value = deviceSn.value.copy(
                            value = event.text.deviceSn
                            )
                    deviceIn.value = deviceIn.value.copy(
                            value = event.text.deviceIn
                            )
                    recipient.value = recipient.value.copy(
                        value = event.text.recipient
                    )
                    defectDescription.value = defectDescription.value.copy(
                        value = event.text.defectDescription
                    )
                    repairDescription.value = repairDescription.value.copy(
                        value = event.text.repairDescription
                    )
                    partDescription.value = partDescription.value.copy(
                        value = event.text.partDescription
                    )
                }
                is UiEvent.ShowSnackBar -> {
                    coroutineScope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = event.messege,
                        )
                    }
                }
                is UiEvent.NavigateTo -> {
                    navHostController.navigate(event.route)
                }
                is UiEvent.SetFieldsIsEditable -> {
                    deviceName.value = deviceName.value.copy(clickable = event.value)
                    deviceManufacturer.value =
                        deviceManufacturer.value.copy(clickable = event.value)
                    deviceModel.value = deviceModel.value.copy(clickable = event.value)
                    deviceSn.value = deviceSn.value.copy(clickable = event.value)
                    deviceIn.value = deviceIn.value.copy(clickable = event.value)
                    ward.value = ward.value.copy(clickable = event.value)
                    comment.value = comment.value.copy(clickable = event.value)
                    recipient.value = recipient.value.copy(clickable = event.value)
                    defectDescription.value = defectDescription.value.copy(clickable = event.value)
                    repairDescription.value = repairDescription.value.copy(clickable = event.value)
                    partDescription.value = partDescription.value.copy(clickable = event.value)
                }
            }
        }
    }


    Scaffold(

/* ********************** FLOATING BUTTON  ****************************************************** */
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (repairDetailsState.value.isInEditMode) {
                        if(repairDetailsState.value.repair.repairId != "0") {
                            viewModel.onEvent(RepairDetailsEvent.UpdateRepair(repairDetailsState.value.repair))
                        } else {
                            viewModel.onEvent(RepairDetailsEvent.SaveRepair(repairDetailsState.value.repair))
                        }
                        navHostController.navigate(Screens.RepairListScreen.route)

                        // For testing only
                        // viewModel.onEvent(RepairDetailsEvent.SetIsInEditMode(false))
                    } else {
                        viewModel.onEvent(RepairDetailsEvent.SetIsInEditMode(true))
                    }

                },
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = if(repairDetailsState.value.isInEditMode) Icons.Default.Save else Icons.Default.Edit,
                    contentDescription = "Save",
                    modifier = Modifier,
                tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
                .verticalScroll(scrollState)
                ) {

/* ********************** OPENING DATE  ********************************************************* */
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    enabled = isInEditMode,
                    onClick = { openingDateDialogState.show() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        disabledBackgroundColor = MaterialTheme.colorScheme.secondary,
                    ),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary)
                ) {
                    Text(
                        text = "Opening date: " + Helper.getDateString(repairDetailsState.value.repair.openingDate.toLong()),
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
/* ********************** PICKUP TECHNICIAN  **************************************************** */

                Text(
                    text = "Pickup technician:",
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(start = 8.dp)
                )

                DefaultSelectionSection(
                    itemList = technicianList,
                    nameList = technicianList.map { it.name },
                    selectedItem = technicianList.find { (it.technicianId == repairDetailsState.value.repair.pickupTechnicianId) }
                        ?: Technician(),
                    onItemChanged = {
                        viewModel.onEvent(
                            RepairDetailsEvent.UpdateRepairState(
                                repairDetailsState.value.repair.copy(
                                    pickupTechnicianId = it.technicianId
                                )
                            )
                        )
                    },
                    enabled = isInEditMode
                )

/* ********************** DEVICE  *************************************************************** */
            Text(
                text = "Device",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.height(2.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DefaultTextField(
                onValueChanged = { name ->
                    deviceName.value = deviceName.value.copy(value = name)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                deviceName = name
                            )
                        )
                    )
                },
                state = deviceName
            )
            DefaultTextField(
                onValueChanged = { manufacturer ->
                    deviceManufacturer.value = deviceManufacturer.value.copy(value = manufacturer)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                deviceManufacturer = manufacturer
                            )
                        )
                    )
                },
                state = deviceManufacturer
            )
            DefaultTextField(
                onValueChanged = { model ->
                    deviceModel.value = deviceModel.value.copy(value = model)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                deviceModel = model
                            )
                        )
                    )
                },
                state = deviceModel
            )
            DefaultTextField(
                onValueChanged = { serialNumber ->
                    deviceSn.value = deviceSn.value.copy(value = serialNumber)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                deviceSn = serialNumber
                            )
                        )
                    )
                },
                state = deviceSn
            )
            DefaultTextField(
                onValueChanged = { inventoryNumber ->
                    deviceIn.value = deviceIn.value.copy(value = inventoryNumber)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                deviceIn = inventoryNumber
                            )
                        )
                    )
                },
                state = deviceIn
            )

/* ********************** LOCALIZATION  ********************************************************* */
            Text(
                text = "Localization",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.height(2.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

/* ********************** HOSPITAL SELECTION **************************************************** */

            DefaultSelectionSection(
                itemList = hospitalList,
                nameList = hospitalList.map { it.hospital },
                selectedItem = hospitalList.find { (it.hospitalId == repairDetailsState.value.repair.hospitalId) }
                    ?: Hospital(),
                onItemChanged = {
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                hospitalId = it.hospitalId
                            )
                        )
                    )
                },
                enabled = isInEditMode
            )
            DefaultTextField(
                onValueChanged = { string ->
                    ward.value = ward.value.copy(value = string)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                ward = string
                            )
                        )
                    )
                },
                state = ward
            )
            DefaultTextField(
                onValueChanged = { string ->
                    comment.value = comment.value.copy(value = string)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                comment = string
                            )
                        )
                    )
                },
                state = comment
            )

/* ********************** REPAIR  *************************************************************** */
            Text(
                text = "Repair",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.height(2.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DefaultTextField(
                onValueChanged = { defect ->
                    defectDescription.value = defectDescription.value.copy(value = defect)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                defectDescription = defect
                            )
                        )
                    )
                },
                state = defectDescription
            )
            DefaultTextField(
                onValueChanged = { repair ->
                    repairDescription.value = repairDescription.value.copy(value = repair)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                repairDescription = repair
                            )
                        )
                    )
                },
                state = repairDescription
            )
            DefaultTextField(
                onValueChanged = { part ->
                    partDescription.value = partDescription.value.copy(value = part)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                partDescription = part
                            )
                        )
                    )
                },
                state = partDescription
            )

/* ********************** REPAIRING TECHNICIAN  ************************************************* */
                Text(
                    text = "Repairing technician:",
                    color = MaterialTheme.colorScheme.onSecondary
                )
                DefaultSelectionSection(
                    itemList = technicianList,
                    nameList = technicianList.map { it.name },
                    selectedItem = technicianList.find { (it.technicianId == repairDetailsState.value.repair.repairTechnicianId) }
                        ?: Technician(),
                    onItemChanged =  {
                        viewModel.onEvent(
                            RepairDetailsEvent.UpdateRepairState(
                                repairDetailsState.value.repair.copy(
                                    repairTechnicianId = it.technicianId
                                )
                            )
                        )
                    },
                    enabled = isInEditMode
                )

/* ********************** REPAIRING DATE  ******************************************************* */

            Button(
                enabled = isInEditMode,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { repairingDateDialogState.show()},
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    disabledBackgroundColor =  MaterialTheme.colorScheme.secondary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary)
            ) {
                Text(
                    text = "Repairing date: " + Helper.getDateString(repairDetailsState.value.repair.repairingDate.toLong()),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

/* ********************** RESULT  *************************************************************** */
            Text(
                text = "Result",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.height(2.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

/* ********************** EST STATE  ************************************************************ */
            Text(
                text = "EstState:",
                color = MaterialTheme.colorScheme.onSecondary
                )
            DefaultSelectionSection(
                itemList = estStateList,
                nameList = estStateList.map { it.estState },
                selectedItem = estStateList.find { (it.estStateId == repairDetailsState.value.repair.estStateId) }
                    ?: EstState(),
                onItemChanged =  {
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                estStateId = it.estStateId
                            )
                        )
                    )
                },
                enabled = isInEditMode
            )

/* ********************** REPAIRING STATE  ****************************************************** */
            Text(
                text = "RepairState:",
                color = MaterialTheme.colorScheme.onSecondary
            )
            DefaultSelectionSection(
                itemList = repairStateList,
                nameList = repairStateList.map { it.repairState },
                selectedItem = repairStateList.find { (it.repairStateId == repairDetailsState.value.repair.repairStateId) }
                    ?: RepairState(),
                onItemChanged = {
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                repairStateId = it.repairStateId
                            )
                        )
                    )
                },
                enabled = isInEditMode
            )

/* ********************** RETURN TECHNICIAN  **************************************************** */
            Text(
                text = "Return technician:",
                color = MaterialTheme.colorScheme.onSecondary
            )
            DefaultSelectionSection(
                itemList = technicianList,
                nameList = technicianList.map { it.name },
                selectedItem = technicianList.find { (it.technicianId == repairDetailsState.value.repair.returnTechnicianId) }
                    ?: Technician(),
                onItemChanged = {
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                returnTechnicianId = it.technicianId
                            )
                        )
                    )
                },
                enabled = isInEditMode
            )

/* ********************** RETURNING DATE  **************************************************** */
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { returningDateDialogState.show()},
                enabled = isInEditMode,
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    disabledBackgroundColor = MaterialTheme.colorScheme.secondary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary)
            ) {
                Text(
                    text = "Returning date: " + Helper.getDateString(repairDetailsState.value.repair.closingDate.toLong()),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            DefaultTextField(
                onValueChanged = { string ->
                    recipient.value = recipient.value.copy(value = string)
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                recipient = string
                            )
                        )
                    )
                },
                state = recipient
            )

/* ********************** SIGNATURE  ************************************************************ */
            Button(
                modifier = Modifier
                    .padding(8.dp),
                enabled = isInEditMode,
                onClick = { signatureDialogState.show()},
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    disabledBackgroundColor = MaterialTheme.colorScheme.secondary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = repairDetailsState.value.signature.asImageBitmap(),
                    contentDescription = "Signature")
            }

/* ********************** DIALOGS  ************************************************************** */
            DefaultDatePickerDialog(
                dialogState = repairingDateDialogState,
                onClick = {
                    repairingDateState.value = it
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                repairingDate = it.atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli().toString()
                            )
                        )
                    )
                },
                title = "RepairingDate",
                initialDate = Instant.ofEpochMilli(repairDetailsState.value.repair.repairingDate.toLong())
                    .atZone(
                        ZoneId.systemDefault()
                    ).toLocalDate()
            )
            DefaultDatePickerDialog(
                dialogState = openingDateDialogState,
                onClick = {
                    openingDateState.value = it
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                openingDate = it.atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli().toString()
                            )
                        )
                    )
                },
                title = "OpeningDate",
                initialDate = Instant.ofEpochMilli(repairDetailsState.value.repair.openingDate.toLong())
                    .atZone(
                        ZoneId.systemDefault()
                    ).toLocalDate()
            )
            DefaultDatePickerDialog(
                dialogState = returningDateDialogState,
                onClick = {
                    returningDateState.value = it
                    viewModel.onEvent(
                        RepairDetailsEvent.UpdateRepairState(
                            repairDetailsState.value.repair.copy(
                                closingDate = it.atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli().toString()
                            )
                        )
                    )
                },
                title = "ReturningDate",
                initialDate = Instant.ofEpochMilli(repairDetailsState.value.repair.closingDate.toLong())
                    .atZone(
                        ZoneId.systemDefault()
                    ).toLocalDate()
            )
            if (showExitDialog.value) {
                    ExitAlertDialog(
                        title = "Save?",
                        contentText = "Do you want save changes?",
                        onConfirm = {
                            if (showExitDialog.value) {
                                if (repairDetailsState.value.repair.repairId != "0") {
                                    viewModel.onEvent(
                                        RepairDetailsEvent.UpdateRepair(
                                            repairDetailsState.value.repair
                                        )
                                    )
                                } else {
                                    viewModel.onEvent(
                                        RepairDetailsEvent.SaveRepair(
                                            repairDetailsState.value.repair
                                        )
                                    )
                                }
                            }
                            navHostController.popBackStack()

                        },
                        onDismiss = { showExitDialog.value = false },
                        onDismissRequest = { showExitDialog.value = false }
                    )
            }

            MaterialDialog(
                dialogState = signatureDialogState,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                backgroundColor = MaterialTheme.colorScheme.primary,
                buttons = {
                    positiveButton(
                        text = "Confirm",
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
                    )
                    negativeButton(
                        text = "Cancel",
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
                    )
                }
            ) {
                customView {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center

                    ){
                        SignatureArea { bitmap ->
                            viewModel.onEvent(RepairDetailsEvent.UpdateSignatureState(bitmap))
                        }
                    }
                }
            }
        }
    }
    if (repairDetailsState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

