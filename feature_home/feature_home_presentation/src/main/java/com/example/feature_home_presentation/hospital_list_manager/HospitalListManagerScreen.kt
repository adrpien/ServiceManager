package com.example.feature_home_presentation.hospital_list_manager

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiText
import com.example.core_ui.components.list_manager.ManagerAddListItem
import com.example.core_ui.components.list_manager.ManagerListItem
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.core_ui.components.textfield.DefaultTextField
import com.example.core_ui.components.textfield.DefaultTextFieldState
import com.example.feature_home_presentation.R
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.customView
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun HospitalListManagerScreen(
    viewModel: HospitalListManagerViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val hospitalList = viewModel.hospitalListState.collectAsState().value

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val addHospitalDialogState = rememberMaterialDialogState()
    val addHospitalState = remember { mutableStateOf(DefaultTextFieldState(hint = "Hospital name")) }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = UiText.StringResource(R.string.undo).asString(context),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) {
                AppSnackbar(
                    data = it,
                    onActionClick = {
                        if(viewModel.lastDeletedHospital != null) {
                            val lastDeletedHospital = viewModel.lastDeletedHospital
                            if (lastDeletedHospital != null) {
                                viewModel.onEvent(HospitalListManagerEvent.RevertHospital(lastDeletedHospital))
                            }
                            }
                    }
                )
            }
        },
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(it)
            .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = stringResource(R.string.hospital_list),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Divider(
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.height(2.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    LazyColumn {
                        if (hospitalList != null) {
                            items(hospitalList.size, key = { it }) { index ->
                                ManagerListItem(
                                    title = hospitalList[index].hospital,
                                    description = hospitalList[index].hospitalId,
                                    icon = Icons.Default.Delete,
                                    iconDescription = stringResource(R.string.delete)
                                ) {
                                    viewModel.onEvent(
                                        HospitalListManagerEvent.DeleteHospital(hospitalList[index])
                                    )
                                }
                            }
                            item {
                                ManagerAddListItem(
                                    icon = Icons.Default.Add,
                                    iconDescription = stringResource(id = R.string.add),
                                ) {
                                    addHospitalDialogState.show()
                                }
                            }
                        }
                    }
                }
                MaterialDialog(
                    dialogState = addHospitalDialogState,
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    ),
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium,
                    buttons = {
                        positiveButton(
                            text = stringResource(R.string.confirm),
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary
                            ),
                            onClick = {
                                viewModel.onEvent(HospitalListManagerEvent.AddHospital(
                                    Hospital(
                                        hospitalId = "0",
                                        hospital = addHospitalState.value.value
                                    )
                                ))
                                addHospitalState.value = addHospitalState.value.copy(value = "")
                            }
                        )
                        negativeButton(
                            text = stringResource(R.string.cancel),
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary
                            ),
                            onClick = {
                                addHospitalState.value = addHospitalState.value.copy(value = "")
                            }
                        )
                    }
                ) {
                    customView {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Spacer(modifier = Modifier.height(8.dp))
                                DefaultTextField(
                                    onValueChanged = { string ->
                                        addHospitalState.value =
                                            addHospitalState.value.copy(value = string)
                                    },
                                    state = addHospitalState
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
