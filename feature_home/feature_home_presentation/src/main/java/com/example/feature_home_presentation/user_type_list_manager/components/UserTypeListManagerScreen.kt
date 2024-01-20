package com.example.feature_home_presentation.technician_list_manager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.components.list_manager.ManagerActionListItem
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.feature_home_presentation.R
import com.example.feature_home_presentation.user_type_list_manager.UiEvent
import com.example.feature_home_presentation.user_type_list_manager.UserTypeListManagerViewModel
import com.example.feature_home_presentation.user_type_list_manager.UserTypeManagerEvent
import com.example.feature_home_presentation.user_type_list_manager.components.UserTypeDialog
import com.example.feature_home_presentation.user_type_list_manager.components.UserTypeManagerListItem
import com.example.servicemanager.feature_app_domain.model.UserType
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
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val userTypeState = remember {
        mutableStateOf(UserType())
    }
    val userTypeListState = viewModel.userTypeListState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.ShowSnackBar ->
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                    }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) {
                AppSnackbar(
                    data = it) {
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
                                userTypeDialogState.show()
                            }
                        )
                    }
                    item {
                        ManagerActionListItem(
                            icon = Icons.Default.Add,
                            iconDescription = stringResource(id = R.string.add),
                        ) {
                            userTypeState.value = UserType(userTypeId = "0")
                            userTypeDialogState.show()
                        }
                    }
                }
            }
            UserTypeDialog(
                userType = userTypeState.value,
                userTypeDialogState = userTypeDialogState
            ) { userType ->
                if(userType.userTypeId == "0") {
                    viewModel.onEvent(UserTypeManagerEvent.AddUserType(userType))
                } else {
                    viewModel.onEvent(UserTypeManagerEvent.UpdateUserType(userType))
                }
            }
        }
    }
}
