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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_inspections_presentation.inspection_list.InspectionListEvent
import com.example.servicemanager.feature_inspections_presentation.inspection_list.InspectionListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.core.util.Screens
import com.example.core_ui.components.other.DefaultSelectionSection
import com.example.servicemanager.feature_inspections_presentation.inspection_list.UiEvent

@Composable
fun InspectionListScreen(
    userId: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: InspectionListViewModel = hiltViewModel(),
    ) {

    val context = LocalContext.current
    val inspectionListState = viewModel.inspectionListState
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = inspectionListState.value.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Screens.InspectionDetailsScreen.withArgs("0"))
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
        scaffoldState = scaffoldState
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
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        textColor = MaterialTheme.colorScheme.onSecondary,
                        cursorColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    onValueChange = {
                        viewModel.onEvent(InspectionListEvent.onSearchQueryChange(it))
                    },
                    modifier = Modifier
                        .padding(10.dp),

                    placeholder = {
                        Text(text = "Search...")
                    },
                    maxLines = 1,
                    singleLine = true,
                )
                IconButton(onClick = { viewModel.onEvent(InspectionListEvent.ToggleSortSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                    tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                IconButton(onClick = { viewModel.onEvent(InspectionListEvent.ToggleHospitalFilterSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.House,
                        contentDescription = "Hospital",
                    tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSecondary)
            )

            Box() {
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
                                            Screens.InspectionDetailsScreen.withArgs(
                                                inspectionListState.value.inspectionList[index].inspectionId
                                            )
                                        )
                                    },
                                inspection = inspectionListState.value.inspectionList[index],
                                hospitalList = inspectionListState.value.hospitalList,
                                technicianList = inspectionListState.value.technicianList,
                                inspectionStateList = inspectionListState.value.inspectionStateList
                            ) {
                                viewModel.onEvent(InspectionListEvent.CopyToClipboard(
                                    string = "some string",
                                    context = context
                                ))
                            }
                        }

                    }
                    this@Column.AnimatedVisibility(
                        visible = inspectionListState.value.isHospitalFilterSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {


                        var itemList = inspectionListState.value.hospitalList + Hospital(
                            hospitalId = "0",
                            hospital = "All"
                        )
                        val hospitalNameList = itemList.map { it.hospital }
                        val accessedHospitalIdList = viewModel.inspectionListState.value.userTypeList.first { it.userTypeId == inspectionListState.value.user.userType }.hospitals

                        itemList = itemList.filter { hospital ->
                            accessedHospitalIdList.contains(hospital.hospitalId)
                        }

                        DefaultSelectionSection(
                            itemList = itemList,
                            nameList = hospitalNameList,
                            selectedItem = inspectionListState.value.hospital ?: Hospital(),
                            onItemChanged = {
                                viewModel.onEvent(
                                    InspectionListEvent.filterInspectionListByHospital(
                                        hospital = it
                                    )
                                )
                            },
                            enabled = true
                        )
                    }

                    this@Column.AnimatedVisibility(
                        visible = inspectionListState.value.isSortSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        InspectionSortSection(
                            onOrderChange = { viewModel.onEvent(InspectionListEvent.orderInspectionList(it)) },
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

