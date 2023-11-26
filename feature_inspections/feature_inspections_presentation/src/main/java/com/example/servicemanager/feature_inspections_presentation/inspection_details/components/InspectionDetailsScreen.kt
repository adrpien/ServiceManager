package com.example.servicemanager.feature_inspections_presentation.inspection_details.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige
import com.example.core.util.Helper
import com.example.core.util.Screens
import com.example.core_ui.components.other.DefaultDatePickerDialog
import com.example.core_ui.components.other.DefaultSelectionSection
import com.example.core_ui.components.other.DefaultTextField
import com.example.core_ui.components.other.DefaultTextFieldState
import com.example.core_ui.components.other.alert_dialogs.ExitAlertDialog
import com.example.core_ui.components.signature.SignatureArea
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_inspections_presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.feature_inspections_presentation.inspection_details.InspectionDetailsViewModel
import com.example.servicemanager.feature_inspections_presentation.inspection_details.InspectionDetailsViewModel.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.customView
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId


@Composable
fun InspectionDetailsScreen(
    inspectionId: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: InspectionDetailsViewModel = hiltViewModel(),
) {

    /* ********************** STATES **************************************************************** */
    val inspectionDetailsState = viewModel.inspectionDetailsState
    val hospitalList = viewModel.inspectionDetailsState.value.hospitalList
    val estStateList = viewModel.inspectionDetailsState.value.estStateList
    val inspectionStateList = viewModel.inspectionDetailsState.value.inspectionStateList
    val technicianList = viewModel.inspectionDetailsState.value.technicianList
    val isInEditMode = viewModel.inspectionDetailsState.value.isInEditMode

    /* ********************** DIALOGS *************************************************************** */
    val inspectionDateDialogState = rememberMaterialDialogState()
    val inspectionDateState = remember {
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
                value = inspectionDetailsState.value.inspection.deviceName,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Manufacturer",
                value = inspectionDetailsState.value.inspection.deviceManufacturer,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Model",
                value = inspectionDetailsState.value.inspection.deviceModel,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val deviceSn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Serial number",
                value = inspectionDetailsState.value.inspection.deviceSn,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val deviceIn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Inventory number",
                value = inspectionDetailsState.value.inspection.deviceIn,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Ward",
                value = inspectionDetailsState.value.inspection.ward,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val comment = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Comment",
                value = inspectionDetailsState.value.inspection.comment,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val recipient = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Recipient",
                value = inspectionDetailsState.value.inspection.recipient,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }

    BackHandler(isInEditMode) {
        showExitDialog.value = true
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
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
                }
            }
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isInEditMode) {
                        if (inspectionDetailsState.value.inspection.inspectionId != "0") {
                            viewModel.onEvent(
                                InspectionDetailsEvent.UpdateInspection(
                                    inspectionDetailsState.value.inspection
                                )
                            )
                        } else {
                            viewModel.onEvent(
                                InspectionDetailsEvent.SaveInspection(
                                    inspectionDetailsState.value.inspection
                                )
                            )
                        }
                        navHostController.navigate(Screens.InspectionListScreen.route)
                    } else {
                        viewModel.onEvent(InspectionDetailsEvent.SetIsInEditMode(!isInEditMode))
                    }
                },
                backgroundColor = LightBlue
            ) {
                if (isInEditMode) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save",
                        modifier = Modifier,
                        tint = MaterialTheme.colors.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier,
                        tint = MaterialTheme.colors.primary
                    )
                }

            }
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(LightBeige)
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Device",
                fontSize = 20.sp,
                color = LightBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = LightBlue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DefaultTextField(
                onValueChanged = { name ->
                    deviceName.value = deviceName.value.copy(value = name)
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(deviceName = name)
                        )
                    )
                },
                state = deviceName
            )
            DefaultTextField(
                onValueChanged = { manufacturer ->
                    deviceManufacturer.value =
                        deviceManufacturer.value.copy(value = manufacturer)
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(deviceManufacturer = manufacturer)
                        )
                    )
                },
                state = deviceManufacturer
            )
            DefaultTextField(
                onValueChanged = { model ->
                    deviceModel.value = deviceModel.value.copy(value = model)
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(deviceModel = model)
                        )
                    )
                },
                state = deviceModel
            )
            DefaultTextField(
                onValueChanged = { serialNumber ->
                    deviceSn.value = deviceSn.value.copy(value = serialNumber)
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(deviceSn = serialNumber)
                        )
                    )
                },
                state = deviceSn
            )
            DefaultTextField(
                onValueChanged = { inventoryNumber ->
                    deviceIn.value = deviceIn.value.copy(value = inventoryNumber)
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(deviceIn = inventoryNumber)
                        )
                    )
                },
                state = deviceIn
            )
            Text(
                text = "Localization",
                fontSize = 20.sp,
                color = LightBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = LightBlue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            DefaultSelectionSection(
                itemList = hospitalList,
                nameList = hospitalList.map { it.hospital },
                selectedItem = hospitalList.find { (it.hospitalId == inspectionDetailsState.value.inspection.hospitalId) }
                    ?: Hospital(),
                onItemChanged = {
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.copy(
                                inspection = inspectionDetailsState.value.inspection.copy(
                                    hospitalId = it.hospitalId
                                )
                            ).inspection
                        )
                    )
                },
                enabled = isInEditMode
            )

            DefaultTextField(
                onValueChanged = { string ->
                    ward.value = ward.value.copy(value = string)
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(ward = string)
                        )
                    )
                },
                state = ward
            )
            DefaultTextField(
                onValueChanged = { string ->
                    comment.value = comment.value.copy(value = string)
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(comment = string)
                        )
                    )
                },
                state = comment
            )
            Text(
                text = "Result",
                fontSize = 20.sp,
                color = LightBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = LightBlue,
                modifier = Modifier.height(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "EstState:",
                color = LightBlue
            )

            DefaultSelectionSection(
                itemList = estStateList,
                nameList = estStateList.map { it.estState },
                selectedItem = estStateList.find {
                    (it.estStateId == inspectionDetailsState.value.inspection.estStateId)
                } ?: EstState(),
                onItemChanged = {
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.copy(
                                inspection = inspectionDetailsState.value.inspection.copy(
                                    estStateId = it.estStateId
                                )
                            ).inspection
                        )
                    )
                },
                enabled = inspectionDetailsState.value.isInEditMode
            )

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "InspectionState:",
                color = LightBlue
            )
            DefaultSelectionSection(
                itemList = inspectionStateList,
                nameList = inspectionStateList.map { it.inspectionState },
                selectedItem = inspectionStateList.find { (it.inspectionStateId == inspectionDetailsState.value.inspection.inspectionStateId) }
                    ?: InspectionState(),
                onItemChanged = {
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.copy(
                                inspection = inspectionDetailsState.value.inspection.copy(
                                    inspectionStateId = it.inspectionStateId
                                )
                            ).inspection
                        )
                    )
                },
                enabled = isInEditMode
            )
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Technician:",
                color = LightBlue
            )
            DefaultSelectionSection(
                itemList = technicianList,
                nameList = technicianList.map { it.name },
                selectedItem = technicianList.find { (it.technicianId == inspectionDetailsState.value.inspection.technicianId) }
                    ?: Technician(),
                onItemChanged = {
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.copy(
                                inspection = inspectionDetailsState.value.inspection.copy(
                                    technicianId = it.technicianId
                                )
                            ).inspection
                        )
                    )
                },
                enabled = isInEditMode
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { inspectionDateDialogState.show() },
                enabled = isInEditMode,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LightBeige,
                    contentColor = LightBlue
                ),
                border = BorderStroke(2.dp, LightBlue)
            ) {
                Text(
                    text = "Inspection date: " + Helper.getDateString(inspectionDetailsState.value.inspection.inspectionDate.toLong())
                )
            }
            DefaultTextField(
                onValueChanged = { string ->
                    recipient.value = recipient.value.copy(value = string)
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(recipient = string)
                        )
                    )
                },
                state = recipient
            )
            Button(
                modifier = Modifier
                    .padding(8.dp),
                onClick = { signatureDialogState.show() },
                enabled = isInEditMode,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LightBeige,
                    contentColor = LightBlue
                ),
                border = BorderStroke(2.dp, LightBlue)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = inspectionDetailsState.value.signature.asImageBitmap(),
                    contentDescription = "Signature"
                )
            }
            if(showExitDialog.value) {
                ExitAlertDialog(
                    title = "Save?",
                    contentText = "Do you want save changes?",
                    onConfirm = {
                        if (showExitDialog.value) {
                            if (inspectionDetailsState.value.inspection.inspectionId != "0") {
                                viewModel.onEvent(
                                    InspectionDetailsEvent.UpdateInspection(
                                        inspectionDetailsState.value.inspection
                                    )
                                )
                            } else {
                                viewModel.onEvent(
                                    InspectionDetailsEvent.SaveInspection(
                                        inspectionDetailsState.value.inspection
                                    )
                                )
                            }
                        }
                        viewModel.onEvent(InspectionDetailsEvent.SetIsInEditMode(false))
                        navHostController.popBackStack()

                    },
                    onDismiss = { showExitDialog.value = false },
                    onDismissRequest = { showExitDialog.value = false }
                )
            }
            DefaultDatePickerDialog(
                dialogState = inspectionDateDialogState,
                onClick = {
                    inspectionDateState.value = it
                    viewModel.onEvent(
                        InspectionDetailsEvent.UpdateInspectionState(
                            inspectionDetailsState.value.inspection.copy(
                                inspectionDate = it.atStartOfDay(ZoneId.systemDefault())
                                    .toInstant().toEpochMilli().toString()
                            )
                        )
                    )
                },
                title = "Inspection Date"
            )
            MaterialDialog(
                dialogState = signatureDialogState,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                backgroundColor = LightBeige,
                buttons = {
                    positiveButton(
                        text = "Confirm",
                        textStyle = TextStyle(color = LightBlue)
                    ) {}
                    negativeButton(
                        text = "Cancel",
                        textStyle = TextStyle(color = LightBlue)
                    )
                }
            ) {
                customView {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SignatureArea() { bitmap ->
                            viewModel.onEvent(InspectionDetailsEvent.UpdateSignatureState(bitmap))
                        }
                    }
                }
            }
        }
    }
    if (inspectionDetailsState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBeige),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = LightBlue
            )
        }
    }
}




