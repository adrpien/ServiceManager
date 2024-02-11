package com.example.servicemanager.future_repairs_presentation.repair_details.components

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.util.Helper.Companion.toDp
import com.example.core_ui.components.other.DefaultDateButton
import com.example.core_ui.components.textfield.DefaultTextField
import com.example.core_ui.components.textfield.DefaultTextFieldState
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.future_repairs_presentation.repair_details.RepairDetailsEvent
import com.example.servicemanager.future_repairs_presentation.repair_details.RepairDetailsViewModel
import com.example.servicemanager.future_repairs_presentation.repair_details.RepairDetailsViewModel.*
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import com.example.core_ui.components.alert_dialogs.DefaultDatePickerDialog
import com.example.core_ui.components.other.DefaultSelectionSection
import com.example.feature_repairs_presentation.R
import androidx.compose.material3.SnackbarHostState
import com.example.core.util.Dimensions
import com.example.core_ui.components.alert_dialogs.ExitAlertDialog
import com.example.core_ui.components.signature.SignatureDialog
import com.example.core_ui.components.snackbar.AppSnackbar


@Composable
fun RepairDetailsScreen(
    navHostController: NavHostController,
    viewModel: RepairDetailsViewModel = hiltViewModel(),
) {

    val context: Context = LocalContext.current

/* ********************** STATES **************************************************************** */
    val repairDetailsState = viewModel.repairDetailsState.collectAsState()
    val hospitalList = repairDetailsState.value.hospitalList
    val estStateList = repairDetailsState.value.estStateList
    val repairStateList = repairDetailsState.value.repairStateList
    val technicianList = repairDetailsState.value.technicianList
    val isInEditMode = repairDetailsState.value.isInEditMode

/* ********************** DIALOGS *************************************************************** */
    val repairingDateDialogState = rememberMaterialDialogState()
    val repairingDateState = remember {
        mutableStateOf(
        Instant.ofEpochMilli(repairDetailsState.value.repair.repairingDate.toLong())
            .atZone(
                ZoneId.systemDefault()
            ).toLocalDate()
        )
    }

    val openingDateDialogState = rememberMaterialDialogState()
    val openingDateState = remember {
        mutableStateOf(Instant.ofEpochMilli(repairDetailsState.value.repair.openingDate.toLong())
            .atZone(
                ZoneId.systemDefault()
            ).toLocalDate()
        )
    }

    val returningDateDialogState = rememberMaterialDialogState()
    val returningDateState = remember {
        mutableStateOf(
            Instant.ofEpochMilli(repairDetailsState.value.repair.closingDate.toLong())
                .atZone(
                    ZoneId.systemDefault()
                ).toLocalDate()
        )
    }

    val exitDialogState = rememberMaterialDialogState()
    val signatureDialogState = rememberMaterialDialogState()

/* ********************** OTHERS **************************************************************** */
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val deviceName = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.name),
                value = repairDetailsState.value.repair.deviceName,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.manufacturer),
                value = repairDetailsState.value.repair.deviceManufacturer,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.model),
                value = repairDetailsState.value.repair.deviceModel,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val deviceSn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.serial_number),
                value = repairDetailsState.value.repair.deviceSn,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val deviceIn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.inventory_number),
                value = repairDetailsState.value.repair.deviceIn,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.ward),
                value = repairDetailsState.value.repair.ward,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val comment = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.comment),
                value = repairDetailsState.value.repair.comment,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val recipient = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.recipient),
                value = repairDetailsState.value.repair.recipient,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val defectDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.defect_description),
                value = repairDetailsState.value.repair.defectDescription,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val repairDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.repair_description),
                value = repairDetailsState.value.repair.repairDescription,
                clickable = repairDetailsState.value.isInEditMode
            )
        )
    }
    val partDescription = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.part_description),
                value = repairDetailsState.value.repair.partDescription,
                clickable = isInEditMode
            )
        )
    }

/* ********************** BACK HANDLER  ********************************************************* */

    BackHandler(isInEditMode) {
        exitDialogState.show()
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
                            message = event.messege.asString(context),
                            duration = SnackbarDuration.Short,
                        )
                    }
                }
                is UiEvent.NavigateTo -> {
                    navHostController.popBackStack()
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
                    } else {
                        viewModel.onEvent(RepairDetailsEvent.SetIsInEditMode(true))
                    }

                },
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = if(repairDetailsState.value.isInEditMode) Icons.Default.Save else Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.save),
                    modifier = Modifier,
                tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) {
                AppSnackbar(
                    data = it,
                    // can be mutableState here, but for me like this is ok
                    onActionClick = {
                        it.dismiss()
                    }
                    )
            }
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {

/* ********************** OPENING DATE  ********************************************************* */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultDateButton(
                    modifier = Modifier.weight(1f),
                    dateLong = repairDetailsState.value.repair.openingDate.toLong(),
                    onClick = { openingDateDialogState.show() },
                    enabled = isInEditMode,
                    precedingTextSource = R.string.opening_date
                )
                Icon(
                    imageVector = Icons.Default.Today,
                    contentDescription = "Today",
                    modifier = if (repairDetailsState.value.isInEditMode) {
                        Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                viewModel.onEvent(
                                    RepairDetailsEvent.UpdateRepairState(
                                        repairDetailsState.value.repair.copy(
                                            openingDate = System.currentTimeMillis().toString()
                                        )
                                    )
                                )
                            }
                    } else {
                        Modifier.padding(end = 8.dp)
                    }
                        .padding(end = 8.dp),
                    tint = if (repairDetailsState.value.isInEditMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            }

            /* ********************** PICKUP TECHNICIAN  **************************************************** */

            Text(
                text = stringResource(R.string.pickup_technician) + ":",
                color = MaterialTheme.colorScheme.onSecondary
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
                text = stringResource(R.string.device),
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
                text = stringResource(R.string.localization),
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
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.hospital) + ":",
                color = MaterialTheme.colorScheme.onSecondary
            )
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
                text = stringResource(id = R.string.repair),
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
                text = stringResource(R.string.repairing_technician) + ":",
                color = MaterialTheme.colorScheme.onSecondary
            )
            DefaultSelectionSection(
                itemList = technicianList,
                nameList = technicianList.map { it.name },
                selectedItem = technicianList.find { (it.technicianId == repairDetailsState.value.repair.repairTechnicianId) }
                    ?: Technician(),
                onItemChanged = {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultDateButton(
                    modifier = Modifier.weight(1f),
                    dateLong = repairDetailsState.value.repair.repairingDate.toLong(),
                    onClick = { repairingDateDialogState.show() },
                    enabled = isInEditMode,
                    precedingTextSource = R.string.repairing_date
                )
                Icon(
                    imageVector = Icons.Default.Today,
                    contentDescription = "Today",
                    modifier = if (repairDetailsState.value.isInEditMode) {
                        Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                viewModel.onEvent(
                                    RepairDetailsEvent.UpdateRepairState(
                                        repairDetailsState.value.repair.copy(
                                            repairingDate = System.currentTimeMillis().toString()
                                        )
                                    )
                                )
                            }
                    } else {
                        Modifier.padding(end = 8.dp)
                    }
                        .padding(end = 8.dp),
                    tint = if (repairDetailsState.value.isInEditMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            }

            /* ********************** RESULT  *************************************************************** */
            Text(
                text = stringResource(R.string.result),
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
                text = stringResource(R.string.eststate) + ":",
                color = MaterialTheme.colorScheme.onSecondary
            )
            DefaultSelectionSection(
                itemList = estStateList,
                nameList = estStateList.map { it.estState },
                selectedItem = estStateList.find { (it.estStateId == repairDetailsState.value.repair.estStateId) }
                    ?: EstState(),
                onItemChanged = {
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
                text = stringResource(R.string.repairstate) + ": ",
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
                text = stringResource(R.string.return_technician) + ":",
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultDateButton(
                    modifier = Modifier.weight(1f),
                    dateLong = repairDetailsState.value.repair.closingDate.toLong(),
                    onClick = { returningDateDialogState.show() },
                    enabled = isInEditMode,
                    precedingTextSource = R.string.returning_date
                )
                Icon(
                    imageVector = Icons.Default.Today,
                    contentDescription = "Today",
                    modifier = if (repairDetailsState.value.isInEditMode) {
                        Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                viewModel.onEvent(
                                    RepairDetailsEvent.UpdateRepairState(
                                        repairDetailsState.value.repair.copy(
                                            closingDate = System.currentTimeMillis().toString()
                                        )
                                    )
                                )
                            }
                    } else {
                        Modifier.padding(end = 8.dp)
                    }
                        .padding(end = 8.dp),
                    tint = if (repairDetailsState.value.isInEditMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            }

            /* ********************** RECIPIENT  ******************************************************** */


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
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = isInEditMode,
                onClick = { signatureDialogState.show() },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    disabledBackgroundColor = MaterialTheme.colorScheme.secondary
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isInEditMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Image(
                    modifier = Modifier
                        .width(Dimensions.signatureWidth.toDp.dp)
                        .height(Dimensions.signatureHeight.toDp.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onSecondary),
                    bitmap = repairDetailsState.value.signature.asImageBitmap(),
                    contentDescription = stringResource(R.string.signature)
                )
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
                title = stringResource(R.string.repairing_date),
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
                title = stringResource(R.string.opening_date),
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
                title = stringResource(R.string.returning_date),
                initialDate = Instant.ofEpochMilli(repairDetailsState.value.repair.closingDate.toLong())
                    .atZone(
                        ZoneId.systemDefault()
                    ).toLocalDate()
            )

            ExitAlertDialog(
                exitAlertDialogState = exitDialogState,
                onConfirm = {
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
                    viewModel.onEvent(RepairDetailsEvent.SetIsInEditMode(false))
                    navHostController.popBackStack()
                            },
                onDismiss = {
                    viewModel.onEvent(RepairDetailsEvent.SetIsInEditMode(false))
                    navHostController.popBackStack()
                })
            SignatureDialog(signatureDialogState = signatureDialogState) {
                viewModel.onEvent(RepairDetailsEvent.UpdateSignatureState(it))
            }
        }
    }
    }
    if (repairDetailsState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

