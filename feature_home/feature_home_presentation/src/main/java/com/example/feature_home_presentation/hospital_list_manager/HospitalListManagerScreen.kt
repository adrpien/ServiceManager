package com.example.feature_home_presentation.hospital_list_manager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Undo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.util.Screen
import com.example.core.util.UiText
import com.example.core_ui.components.list_manager.ManagerListItem
import com.example.core_ui.components.list_manager.ManagerListItem2
import com.example.core_ui.components.signature.SignatureArea
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
    navHostController: NavHostController,
    viewModel: HospitalListManagerViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val hospitalList = viewModel.hospitalListState.value
    val deletedHospitalList = viewModel.deletedHospitalListState.value
    var lastHospital: Hospital? = null

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val addHospitalDialogState = rememberMaterialDialogState()
    val addHospitalState = remember { mutableStateOf(DefaultTextFieldState(
        hint = "Hospital name",
        value =""
    )) }


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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addHospitalDialogState.show()
                },
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add),
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
                    onActionClick = {
                        viewModel.onEvent(HospitalListManagerEvent.UndoChanges)
                    }
                )
            }
        },
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
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
                    LazyColumn() {
                        if (hospitalList != null) {
                            items(hospitalList.size, key = { it }) { index ->
                                ManagerListItem(
                                    title = hospitalList[index].hospital,
                                    description = hospitalList[index].hospitalId,
                                    icon = Icons.Default.Delete,
                                    iconDescription = stringResource(R.string.delete)
                                ) {
                                    viewModel.onEvent(
                                        HospitalListManagerEvent.DeleteHospital(
                                            hospitalList[index].hospitalId
                                        )
                                    )
                                }
                            }
                        }
                    }
                    LazyColumn() {
                        if (deletedHospitalList != null) {
                            items(deletedHospitalList.size, key = { it }) { index ->
                                ManagerListItem(
                                    title = deletedHospitalList[index].hospital,
                                    description = deletedHospitalList[index].hospitalId,
                                    icon = Icons.Default.Undo,
                                    iconDescription = stringResource(R.string.undo)
                                ) {
                                    viewModel.onEvent(
                                        HospitalListManagerEvent.AddHospital(
                                            deletedHospitalList[index]
                                        )
                                    )
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
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium,
                    buttons = {
                        positiveButton(
                            text = stringResource(R.string.confirm),
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                        negativeButton(
                            text = stringResource(R.string.cancel),
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                    }
                ) {
                    customView {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary),
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
