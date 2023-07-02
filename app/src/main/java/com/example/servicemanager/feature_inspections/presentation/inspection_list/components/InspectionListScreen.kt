package com.example.servicemanager.feature_inspections.presentation.inspection_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.servicemanager.feature_app.domain.model.Device
import com.example.servicemanager.feature_inspections.presentation.inspection_list.InspectionListEvent
import com.example.servicemanager.feature_inspections.presentation.inspection_list.InspectionListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun InspectionListScreen(
    modifier: Modifier = Modifier,
    viewModel: InspectionListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
    ) {

    val state = viewModel.state

    val swipeRefreshState = rememberSwipeRefreshState(
    isRefreshing = state.value.isRefreshing
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.onEvent(InspectionListEvent.Refresh) }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.value.inspectionList.size) {index ->
                    InspectionListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // TODO Navigation to InspectionDetailsScreen in InspectionScreen to implement
                            },
                        inspection = state.value.inspectionList[index],
                        device = state.value.deviceList.find { it.deviceId == state.value.inspectionList[index].deviceId } ?: Device(),
                        hospitalList = state.value.hospitalList,
                        technicianList = state.value.technicianList,
                        inspectionStateList = state.value.inspectionStateList

                    )
                    if(index < state.value.inspectionList.size) {
                        Divider(
                            modifier = Modifier
                                .padding(13.dp)
                        )
                    }
                }

            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(state.value.isLoading) {
            CircularProgressIndicator()
        }
    }

}
