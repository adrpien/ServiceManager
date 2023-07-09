package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel


@Composable
fun InspectionDetailsScreen(
    inspectionId: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: InspectionDetailsViewModel = hiltViewModel(),
) {

    val state = viewModel.inspectionDetailsState

    Box(
        modifier = modifier
            .fillMaxSize()
    ){

        val deviceNameState = remember { mutableStateOf<String>(state.value.inspection.deviceName) }

        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = deviceNameState.value,
                label = {
                    Text(text = "Name")
                } ,
                onValueChange = {
                    deviceNameState.value = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}