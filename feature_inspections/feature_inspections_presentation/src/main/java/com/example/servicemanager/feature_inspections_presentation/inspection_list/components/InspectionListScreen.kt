package com.example.servicemanager.feature_inspections_presentation.inspection_list.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_inspections_presentation.inspection_list.InspectionListEvent
import com.example.servicemanager.feature_inspections_presentation.inspection_list.InspectionListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.core.util.Screen
import com.example.core_ui.components.other.HospitalSelectionSection
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.feature_inspections_presentation.R
import com.example.servicemanager.feature_inspections_presentation.inspection_list.UiEvent
import kotlinx.coroutines.launch

@Composable
fun InspectionListScreen(
    navHostController: NavHostController,
    viewModel: InspectionListViewModel = hiltViewModel(),
    ) {

    val context = LocalContext.current
    val inspectionListState = viewModel.inspectionListState.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = inspectionListState.value.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
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
                    navHostController.navigate(Screen.InspectionDetailsScreen.withArgs("0"))
                },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add inspection",
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .height(80.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inspectionListState.value.searchQuery,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        placeholderColor = MaterialTheme.colorScheme.onSecondary,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        cursorColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    onValueChange = {
                        viewModel.onEvent(InspectionListEvent.OnSearchQueryChange(it))
                    },
                    modifier = Modifier
                        .padding(10.dp),

                    placeholder = {
                        Text(text = stringResource(R.string.search))
                    },
                    maxLines = 1,
                    singleLine = true,
                )
                IconButton(onClick = { viewModel.onEvent(InspectionListEvent.ToggleSortSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = stringResource(R.string.sort),
                    tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                IconButton(onClick = { viewModel.onEvent(InspectionListEvent.ToggleHospitalFilterSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.House,
                        contentDescription = stringResource(R.string.hospital),
                    tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSecondary)
            )

            Box {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.onEvent(InspectionListEvent.Refresh)
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(inspectionListState.value.inspectionList.size) { index ->
                            InspectionListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navHostController.navigate(
                                            Screen.InspectionDetailsScreen.withArgs(
                                                inspectionListState.value.inspectionList[index].inspectionId
                                            )
                                        )
                                    },
                                inspection = inspectionListState.value.inspectionList[index],
                                hospitalList = inspectionListState.value.hospitalList,
                                technicianList = inspectionListState.value.technicianList,
                                inspectionStateList = inspectionListState.value.inspectionStateList,
                                onInPress = {
                                    viewModel.onEvent(InspectionListEvent.CopyToClipboard(
                                        string = it,
                                    )
                                    )
                                },
                                onSnPress = {
                                    viewModel.onEvent(InspectionListEvent.CopyToClipboard(
                                        string = it,
                                    )
                                    )

                                }
                            )
                        }

                    }
                    this@Column.AnimatedVisibility(
                        visible = inspectionListState.value.isHospitalFilterSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {


                        var items = inspectionListState.value.hospitalList + Hospital(
                            hospitalId = "0",
                            hospital = "All"
                        )

                        var itemMap: MutableMap<Hospital, Boolean> = mutableMapOf<Hospital, Boolean>()
                        items.forEach { hospital ->
                            var isEnabled = inspectionListState.value.userType.hospitals.contains(hospital.hospitalId)
                            if(hospital.hospitalId == "0") isEnabled = true
                            itemMap.put(hospital, isEnabled)
                        }

                        HospitalSelectionSection(
                            itemList = itemMap,
                            selectedItem = inspectionListState.value.hospital ?: Hospital(),
                            onItemChanged = {
                                viewModel.onEvent(
                                    InspectionListEvent.FilterInspectionListByHospital(
                                       hospital = it
                                    )
                                )
                            }
                        )
                    }

                    this@Column.AnimatedVisibility(
                        visible = inspectionListState.value.isSortSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        InspectionSortSection(
                            onOrderChange = { viewModel.onEvent(InspectionListEvent.OrderInspectionList(it)) },
                            inspectionOrderType = inspectionListState.value.inspectionOrderType,
                            onToggleMonotonicity = {
                                viewModel.onEvent(InspectionListEvent.ToggleOrderMonotonicity(it))
                            }
                        )
                    }
                }
            }
        }
    }
    if (inspectionListState.value.isLoading) {
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


