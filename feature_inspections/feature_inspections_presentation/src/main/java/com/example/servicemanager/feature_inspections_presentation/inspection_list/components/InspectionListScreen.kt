package com.example.servicemanager.feature_inspections_presentation.inspection_list.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
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
import com.example.core.theme.TiemedLightBlue
import com.example.core.theme.TiemedVeryLightBeige
import com.example.feature_app_presentation.components.hospital_filter.HospitalFilterSection
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_inspections_presentation.inspection_list.InspectionListEvent
import com.example.servicemanager.feature_inspections_presentation.inspection_list.InspectionListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.core.util.Screen


@Composable
fun InspectionListScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: InspectionListViewModel = hiltViewModel(),
    ) {


    val state = viewModel.inspectionListState

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.value.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Screen.InspectionDetailsScreen.withArgs("0"))
                },
                backgroundColor = TiemedLightBlue
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add inspection",
                    tint = TiemedVeryLightBeige
                )
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().background(TiemedVeryLightBeige)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.value.searchQuery,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = TiemedLightBlue,
                        unfocusedBorderColor = TiemedLightBlue
                    ),
                    onValueChange = {
                        viewModel.onEvent(InspectionListEvent.onSearchQueryChange(it))
                    },
                    modifier = Modifier
                        .padding(8.dp),

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
                    tint = TiemedLightBlue
                    )
                }
                // TODO All Hospitals filter in InspectionListScreen
                IconButton(onClick = { viewModel.onEvent(InspectionListEvent.ToggleHospitalFilterSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.House,
                        contentDescription = "Hospital",
                    tint = TiemedLightBlue
                    )
                }
                // TODO Import inspections in InspectionListScreen

            }
            AnimatedVisibility(
                visible = state.value.isHospitalFilterSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                HospitalFilterSection(
                    hospitalList = state.value.hospitalList,
                    hospital = state.value.hospital ?: Hospital(),
                    onHospitalChange = { viewModel.onEvent(InspectionListEvent.filterInspectionListByHospital(hospital = it)) }
                )
            }

            AnimatedVisibility(
                visible = state.value.isSortSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                InspectionSortSection(
                    onOrderChange = { viewModel.onEvent(InspectionListEvent.orderInspectionList(it)) },
                    inspectionOrderType = state.value.inspectionOrderType,
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
                    items(state.value.inspectionList.size) { index ->
                        InspectionListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.navigate(Screen.InspectionDetailsScreen.withArgs(state.value.inspectionList[index].inspectionId))
                                },
                            inspection = state.value.inspectionList[index],
                            hospitalList = state.value.hospitalList,
                            technicianList = state.value.technicianList,
                            inspectionStateList = state.value.inspectionStateList

                        )
                    }

                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.value.isLoading) {
                CircularProgressIndicator(
                    color = TiemedLightBlue
                )
            }
        }
    }
}

