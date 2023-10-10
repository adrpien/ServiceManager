package com.example.servicemanager.future_repairs_presentation.repair_list.components

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
import com.example.core.util.Screens
import com.example.feature_app_presentation.components.hospital_filter.HospitalFilterSection
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.future_repairs_presentation.repair_list.RepairListEvent
import com.example.servicemanager.future_repairs_presentation.repair_list.RepairListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RepairListScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: RepairListViewModel = hiltViewModel(),
    ) {


    val repairListState = viewModel.repairListState

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = repairListState.value.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Screens.RepairDetailsScreen.withArgs("0"))
                },
                backgroundColor = TiemedLightBlue
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add repair",
                    tint = TiemedVeryLightBeige
                )
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TiemedVeryLightBeige)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = repairListState.value.searchQuery,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = TiemedLightBlue,
                        unfocusedBorderColor = TiemedLightBlue
                    ),
                    onValueChange = {
                        viewModel.onEvent(RepairListEvent.onSearchQueryChange(it))
                    },
                    modifier = Modifier
                        .padding(8.dp),

                    placeholder = {
                        Text(text = "Search...")
                    },
                    maxLines = 1,
                    singleLine = true,
                )

                IconButton(onClick = { viewModel.onEvent(RepairListEvent.ToggleSortSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                    tint = TiemedLightBlue
                    )
                }
                IconButton(onClick = { viewModel.onEvent(RepairListEvent.ToggleHospitalFilterSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.House,
                        contentDescription = "Hospital",
                    tint = TiemedLightBlue
                    )
                }
            }
            AnimatedVisibility(
                visible = repairListState.value.isHospitalFilterSectionVisible,
                enter = fadeIn() + slideInHorizontally(),
                // exit = fadeOut() + slideOutVertically()
                exit = fadeOut() + slideOutHorizontally()
            ) {
                HospitalFilterSection(
                    hospitalList = repairListState.value.hospitalList + Hospital(hospitalId = "0", hospital = "All"),
                    hospital = repairListState.value.hospital ?: Hospital(),
                    onHospitalChange = { viewModel.onEvent(RepairListEvent.filterRepairListByHospital(hospital = it)) }
                )
            }

            AnimatedVisibility(
                visible = repairListState.value.isSortSectionVisible,
                enter = fadeIn() + slideInHorizontally(),
                // exit = fadeOut() + slideOutVertically()
                exit = fadeOut() + slideOutHorizontally()
            ) {
                Column {
                    RepairSortSection(
                        onOrderChange = { viewModel.onEvent(RepairListEvent.orderRepairList(it)) },
                        repairOrderType = repairListState.value.repairOrderType,
                        onToggleMonotonicity = {
                            viewModel.onEvent(RepairListEvent.ToggleOrderMonotonicity(it))
                        }
                    )
                }
            }
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onEvent(RepairListEvent.Refresh)
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(repairListState.value.repairList.size) { index ->
                        RepairListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.navigate(
                                        Screens.RepairDetailsScreen.withArgs(
                                            repairListState.value.repairList[index].repairId
                                        )
                                    )
                                },
                            repair = repairListState.value.repairList[index],
                            hospitalList = repairListState.value.hospitalList,
                            technicianList = repairListState.value.technicianList,
                            repairStateList = repairListState.value.repairStateList

                        )
                    }

                }
            }
        }
    }
    if (repairListState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TiemedVeryLightBeige),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = TiemedLightBlue
            )
        }
    }
}

