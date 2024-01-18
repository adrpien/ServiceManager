package com.example.servicemanager.feature_inspections_presentation.inspection_details.components

import android.content.Context
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.util.Dimensions
import com.example.core.util.Helper.Companion.toDp
import com.example.core_ui.components.other.DefaultDateButton
import com.example.core_ui.components.alert_dialogs.DefaultDatePickerDialog
import com.example.core_ui.components.other.DefaultSelectionSection
import com.example.core_ui.components.textfield.DefaultTextField
import com.example.core_ui.components.textfield.DefaultTextFieldState
import com.example.core_ui.components.alert_dialogs.ExitAlertDialog
import com.example.core_ui.components.alert_dialogs.SignatureDialog
import com.example.core_ui.components.signature.SignatureArea
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.feature_inspections_presentation.R
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

    val context: Context = LocalContext.current

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
                hint = context.resources.getString(R.string.name),
                value = inspectionDetailsState.value.inspection.deviceName,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.manufacturer),
                value = inspectionDetailsState.value.inspection.deviceManufacturer,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.model),
                value = inspectionDetailsState.value.inspection.deviceModel,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val deviceSn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.serial_number),
                value = inspectionDetailsState.value.inspection.deviceSn,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val deviceIn = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.inventory_number),
                value = inspectionDetailsState.value.inspection.deviceIn,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.ward),
                value = inspectionDetailsState.value.inspection.ward,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val comment = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.comment),
                value = inspectionDetailsState.value.inspection.comment,
                clickable = inspectionDetailsState.value.isInEditMode
            )
        )
    }
    val recipient = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = context.resources.getString(R.string.recipient),
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
                            message = event.messege.asString(context),
                        )
                    }
                }

                is UiEvent.NavigateTo -> {
                    navHostController.popBackStack()
                    navHostController.navigate(event.route + "/{userId}")
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
                    } else {
                        viewModel.onEvent(InspectionDetailsEvent.SetIsInEditMode(!isInEditMode))
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                if (isInEditMode) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = stringResource(R.string.save),
                        modifier = Modifier,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit),
                        modifier = Modifier,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {
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
                text = stringResource(id = R.string.localization),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.height(2.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier,
                text = stringResource(R.string.hospital) + ":",
                color = MaterialTheme.colorScheme.onSecondary
            )
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
                text = stringResource(R.string.result),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.height(2.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier,
                text = stringResource(R.string.eststate) + ":",
                color = MaterialTheme.colorScheme.onSecondary
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
                modifier = Modifier,
                text = stringResource(R.string.inspectionstate) + ":",
                color = MaterialTheme.colorScheme.onSecondary
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
                modifier = Modifier,
                text = stringResource(R.string.technician) + ":",
                color = MaterialTheme.colorScheme.onSecondary
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
            DefaultDateButton(
                dateLong = inspectionDetailsState.value.inspection.inspectionDate.toLong(),
                onClick = { inspectionDateDialogState.show() },
                enabled = isInEditMode,
                precedingTextSource = R.string.inspection_date
            )
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
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { signatureDialogState.show() },
                enabled = isInEditMode,
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    disabledBackgroundColor =  MaterialTheme.colorScheme.secondary
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (viewModel.inspectionDetailsState.value.isInEditMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Image(
                    modifier = Modifier
                        .width(Dimensions.signatureWidth.toDp.dp)
                        .height(Dimensions.signatureHeight.toDp.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onSecondary),
                    bitmap = inspectionDetailsState.value.signature.asImageBitmap(),
                    contentDescription = stringResource(R.string.signature)
                )
            }
            if(showExitDialog.value) {
                ExitAlertDialog(
                    title = stringResource(R.string.save) + "?",
                    contentText = stringResource(R.string.do_you_want_save_changes),
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
                title = stringResource(R.string.inspection_date)
            )

            // TODO extract MaterialDialog to separate composable
            /*MaterialDialog(
                dialogState = signatureDialogState,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                backgroundColor = MaterialTheme.colorScheme.primary,
                buttons = {
                    positiveButton(
                        text = stringResource(R.string.confirm),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
                    ) {}
                    negativeButton(
                        text = stringResource(R.string.cancel),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
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
            }*/
            SignatureDialog(
                signatureDialogState = signatureDialogState,
            ) {
                viewModel.onEvent(InspectionDetailsEvent.UpdateSignatureState(it))
            }
        }
    }
    if (inspectionDetailsState.value.isLoading) {
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




