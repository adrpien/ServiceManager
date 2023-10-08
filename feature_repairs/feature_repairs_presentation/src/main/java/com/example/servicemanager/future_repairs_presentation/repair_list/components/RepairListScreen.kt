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
import com.example.core.util.Screen
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


    val state = viewModel.repairListState

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.value.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Screen.RepairDetailsScreen.withArgs("0"))
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
                // TODO Import repairs in RepairListScreen

            }
            AnimatedVisibility(
                visible = state.value.isHospitalFilterSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                HospitalFilterSection(
                    hospitalList = state.value.hospitalList + Hospital(hospitalId = "0", hospital = "All"),
                    hospital = state.value.hospital ?: Hospital(),
                    onHospitalChange = { viewModel.onEvent(RepairListEvent.filterRepairListByHospital(hospital = it)) }
                )
            }

            AnimatedVisibility(
                visible = state.value.isSortSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
               RepairSortSection(
                    onOrderChange = { viewModel.onEvent(RepairListEvent.orderRepairList(it)) },
                    repairOrderType = state.value.repairOrderType,
                    onToggleMonotonicity = {
                        viewModel.onEvent(RepairListEvent.ToggleOrderMonotonicity(it))
                    }
                )
            }



            val groupedRepairLists = state.value.repairList.groupBy { it.hospitalId }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onEvent(RepairListEvent.Refresh)
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(state.value.repairList.size) { index ->
                        RepairListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navHostController.navigate(Screen.RepairDetailsScreen.withArgs(state.value.repairList[index].repairId))
                                },
                            repair = state.value.repairList[index],
                            hospitalList = state.value.hospitalList,
                            technicianList = state.value.technicianList,
                            repairStateList = state.value.repairStateList

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

