package com.example.feature_home_presentation.hospital_list_manager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core_ui.components.list_manager.ManagerListItem
import com.google.accompanist.swiperefresh.SwipeRefresh

@Composable
fun HospitalListManagerScreen(
    navHostController: NavHostController,
    viewModel: HospitalListManagerViewModel = hiltViewModel(),
) {

    val state = viewModel.hospitalListState.value

    Scaffold {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(){
                    items(state.size) { index ->
                        ManagerListItem(
                            title = "DLUGA",
                            description = "b556chjs43g1ks43dih7326ihd"
                        ){
                            viewModel.onEvent()
                        }
                    }

                }

            }
        }
    }

}