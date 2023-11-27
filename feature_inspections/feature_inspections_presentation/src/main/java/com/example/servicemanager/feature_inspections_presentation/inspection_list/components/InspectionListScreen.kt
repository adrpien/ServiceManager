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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_inspections_presentation.inspection_list.InspectionListEvent
import com.example.servicemanager.feature_inspections_presentation.inspection_list.InspectionListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.core.util.Screens
import com.example.core_ui.components.other.DefaultSelectionSection


@Composable
fun InspectionListScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: InspectionListViewModel = hiltViewModel(),
    ) {


    val inspectionListState = viewModel.inspectionListState

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = inspectionListState.value.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()

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
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary
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
            AnimatedVisibility(
                visible = inspectionListState.value.isHospitalFilterSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {

                val itemList = inspectionListState.value.hospitalList + Hospital(
                    hospitalId = "0",
                    hospital = "All"
                )

                val nameList = itemList.map { it.hospital }

                DefaultSelectionSection(
                    itemList = itemList,
                    nameList = nameList,
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

            AnimatedVisibility(
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

