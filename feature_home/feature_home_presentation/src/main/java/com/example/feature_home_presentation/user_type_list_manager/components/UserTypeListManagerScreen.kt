package com.example.feature_home_presentation.technician_list_manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.core_ui.components.textfield.DefaultTextField
import com.example.core_ui.components.textfield.DefaultTextFieldState
import com.example.feature_home_presentation.R
import com.example.feature_home_presentation.user_type_list_manager.UiEvent
import com.example.feature_home_presentation.user_type_list_manager.UserTypeListManagerViewModel
import com.example.feature_home_presentation.user_type_list_manager.UserTypeManagerEvent
import com.example.feature_home_presentation.user_type_list_manager.components.UserTypeManagerListItem
import com.example.servicemanager.feature_app_domain.model.UserType
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.customView
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch

@Composable
fun UserTypeListManagerScreen(
    viewModel: UserTypeListManagerViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val userTypeDialogState = rememberMaterialDialogState()

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val userTypeState = remember {
        mutableStateOf(UserType())
    }
    val userTypeNameState = remember { mutableStateOf(
        DefaultTextFieldState(
            hint = "UserType name",
        )
    ) }

    val userTypeListState = viewModel.userTypeListState.collectAsState()
    val hospitalListState = viewModel.hospitalListState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.ShowSnackBar ->
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
                            actionLabel = UiText.StringResource(R.string.revert_delete).asString(context)
                        )
                    }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState.snackbarHostState,
            ) {
                AppSnackbar(
                    data = it,
                    ) {
                    val lastDeleteUserType = viewModel.lastDeleteUserType
                    if(lastDeleteUserType != null) {
                        viewModel.onEvent(UserTypeManagerEvent.RevertUserType(lastDeleteUserType))
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(padding)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.user_type_list),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Divider(
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.height(2.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn {
                    items(userTypeListState.value.size, key = { it }) { index ->
                        UserTypeManagerListItem(
                            userType = userTypeListState.value[index],
                            onDeleteClick = {
                                viewModel.onEvent(
                                    UserTypeManagerEvent.DeleteUserType(
                                        userTypeListState.value[index]
                                    )
                                )
                            },
                            onItemClick = {
                                userTypeState.value = userTypeListState.value[index]
                                userTypeNameState.value = userTypeNameState.value.copy( value = userTypeListState.value[index].userTypeName )
                                userTypeDialogState.show()
                            }
                        )
                    }
                    item {
                        ManagerAddListItem(
                            icon = Icons.Default.Add,
                            iconDescription = stringResource(id = R.string.add),
                        ) {
                            userTypeNameState.value = userTypeNameState.value.copy(value = "")
                            userTypeState.value = UserType(userTypeId = "0")
                            userTypeDialogState.show()
                        }
                    }
                }
            }
        }
        MaterialDialog(
            dialogState = userTypeDialogState,
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
                        if(userTypeState.value.userTypeId == "0") {
                            viewModel.onEvent(UserTypeManagerEvent.AddUserType(userTypeState.value))
                        } else {
                            viewModel.onEvent(UserTypeManagerEvent.UpdateUserType(userTypeState.value))
                        }
                    }
                )
                negativeButton(
                    text = stringResource(R.string.cancel),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSecondary
                    ),
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
                                userTypeNameState.value = userTypeNameState.value.copy(value = string)
                                userTypeState.value = userTypeState.value.copy( userTypeName =  string)
                            },
                            state = userTypeNameState,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = UiText.StringResource(R.string.hospital_permission).asString(context),
                            color = MaterialTheme.colorScheme.onSecondary
                            )
                        for (hospital in hospitalListState.value) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = userTypeState.value.hospitals.contains(hospital.hospitalId),
                                    onCheckedChange = {
                                        if(userTypeState.value.hospitals.contains(hospital.hospitalId)) {
                                         userTypeState.value = userTypeState.value.copy(hospitals = userTypeState.value.hospitals.minus(hospital.hospitalId))
                                        } else {
                                            userTypeState.value = userTypeState.value.copy(hospitals = userTypeState.value.hospitals.plus(hospital.hospitalId))
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                                        checkedColor = MaterialTheme.colorScheme.primary,
                                        uncheckedColor = MaterialTheme.colorScheme.primary,
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = hospital.hospital,
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}
