package com.example.servicemanager.feature_inspections.presentation.inspection_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
                            }
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



}
