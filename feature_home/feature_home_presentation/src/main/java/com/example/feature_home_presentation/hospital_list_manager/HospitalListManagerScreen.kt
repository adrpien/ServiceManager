package com.example.feature_home_presentation.hospital_list_manager

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Undo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core_ui.components.list_manager.ManagerListItem
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.feature_home_presentation.R
import com.example.servicemanager.feature_app_domain.model.Hospital
import kotlinx.coroutines.launch

@Composable
fun HospitalListManagerScreen(
    navHostController: NavHostController,
    viewModel: HospitalListManagerViewModel = hiltViewModel(),
) {

    val hospitalList = viewModel.hospitalListState.value
    val deletedHospitalList = viewModel.deletedHospitalListState.value
    var lastHospital: Hospital? = null

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = "Cofnij",
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
                    LazyColumn(){
                        if (hospitalList != null) {
                            items(hospitalList.size) { index ->
                                ManagerListItem(
                                    title = hospitalList[index].hospital,
                                    description = hospitalList[index].hospitalId,
                                    icon =  Icons.Default.Delete
                                ){
                                    viewModel.onEvent(HospitalListManagerEvent.DeleteHospital(hospitalList[index].hospitalId))
                                }

                            }
                        }

                    }

                    LazyColumn(){
                        if (deletedHospitalList != null) {
                            items(deletedHospitalList.size) { index ->
                                ManagerListItem(
                                    title = deletedHospitalList[index].hospital,
                                    description = deletedHospitalList[index].hospitalId,
                                    icon = Icons.Default.Undo
                                ){
                                    viewModel.onEvent(HospitalListManagerEvent.AddHospital(deletedHospitalList[index].hospitalId))
                                }

                            }
                        }

                    }
                }


            }
        }
    }

}