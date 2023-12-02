package com.example.servicemanager.future_repairs_presentation.repair_list.components

import androidx.compose.animation.*
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
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
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige
import com.example.core.util.Screens
import com.example.core_ui.components.other.DefaultSelectionSection
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
                backgroundColor = MaterialTheme.colorScheme.primary
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add repair",
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
                    .height(80.dp)
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = repairListState.value.searchQuery,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        cursorColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    onValueChange = {
                        viewModel.onEvent(RepairListEvent.onSearchQueryChange(it))
                    },
                    modifier = Modifier
                        .padding(10.dp),

                    placeholder = {
                        Text(
                            text = "Search...",
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                )

                IconButton(onClick = { viewModel.onEvent(RepairListEvent.ToggleSortSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                IconButton(onClick = { viewModel.onEvent(RepairListEvent.ToggleHospitalFilterSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.House,
                        contentDescription = "Hospital",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
            Box() {
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
                this@Column.AnimatedVisibility(
                    visible = repairListState.value.isHospitalFilterSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    val itemList = repairListState.value.hospitalList + Hospital(
                        hospitalId = "0",
                        hospital = "All"
                    )
                    val nameList = itemList.map { it.hospital }
                    DefaultSelectionSection(
                        itemList = itemList,
                        nameList = nameList,
                        selectedItem = repairListState.value.hospital ?: Hospital(),
                        onItemChanged = {
                            viewModel.onEvent(
                                RepairListEvent.filterRepairListByHospital(
                                    hospital = it
                                )
                            )
                        },
                        enabled = true
                    )
                }
                this@Column.AnimatedVisibility(
                    visible = repairListState.value.isSortSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
                ) {
                    RepairSortSection(
                        onOrderChange = { viewModel.onEvent(RepairListEvent.orderRepairList(it)) },
                        repairOrderType = repairListState.value.repairOrderType,
                        onToggleMonotonicity = {
                            viewModel.onEvent(RepairListEvent.ToggleOrderMonotonicity(it))
                        }
                    )
                }
            }
        }
        }
        if (repairListState.value.isLoading) {
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



