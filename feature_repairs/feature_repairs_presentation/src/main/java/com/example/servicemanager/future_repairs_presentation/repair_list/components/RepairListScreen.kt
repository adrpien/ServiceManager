package com.example.servicemanager.future_repairs_presentation.repair_list.components

import androidx.compose.animation.*
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.util.Screen
import com.example.core_ui.components.other.DefaultSelectionSection
import com.example.core_ui.components.snackbar.AppSnackbar
import com.example.feature_repairs_presentation.R
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.future_repairs_presentation.repair_list.RepairListEvent
import com.example.servicemanager.future_repairs_presentation.repair_list.RepairListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RepairListScreen(
    userId: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: RepairListViewModel = hiltViewModel(),
    ) {


    val repairListState = viewModel.repairListState.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = repairListState.value.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Screen.RepairDetailsScreen.withArgs("0"))
                },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_repair),
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
                    .height(80.dp)
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = repairListState.value.searchQuery,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        placeholderColor = MaterialTheme.colorScheme.onSecondary,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                        cursorColor = MaterialTheme.colorScheme.onSecondary,
                        textColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onValueChange = {
                        viewModel.onEvent(RepairListEvent.OnSearchQueryChange(it))
                    },
                    modifier = Modifier
                        .padding(10.dp),

                    placeholder = {
                        Text(
                            text = stringResource(R.string.search),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                )

                IconButton(onClick = { viewModel.onEvent(RepairListEvent.ToggleSortSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = stringResource(R.string.sort),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                IconButton(onClick = { viewModel.onEvent(RepairListEvent.ToggleHospitalFilterSectionVisibility) }) {
                    Icon(
                        imageVector = Icons.Default.House,
                        contentDescription = stringResource(R.string.hospital),
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
                                            Screen.RepairDetailsScreen.withArgs(
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
                        hospital = stringResource(R.string.all)
                    )
                    val nameList = itemList.map { it.hospital }
                    DefaultSelectionSection(
                        itemList = itemList,
                        nameList = nameList,
                        selectedItem = repairListState.value.hospital ?: Hospital(),
                        onItemChanged = {
                            viewModel.onEvent(
                                RepairListEvent.FilterRepairListByHospital(
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
                        onOrderChange = { viewModel.onEvent(RepairListEvent.OrderRepairList(it)) },
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



