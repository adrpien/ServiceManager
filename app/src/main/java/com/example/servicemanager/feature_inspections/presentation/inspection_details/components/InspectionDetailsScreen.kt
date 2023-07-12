package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.servicemanager.core.util.DefaultTextFieldState
import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsViewModel.*
import com.example.servicemanager.feature_inspections.presentation.inspection_list.components.InspectionTextField


@Composable
fun InspectionDetailsScreen(
    inspectionId: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: InspectionDetailsViewModel = hiltViewModel(),
) {

    val deviceDetailsState = viewModel.inspectionDetailsState

    val deviceName = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Name",
                text =  deviceDetailsState.value.inspection.deviceName
            )
        )
    }
    val deviceManufacturer = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Manufacturer",
                text =  deviceDetailsState.value.inspection.deviceManufacturer
            )
        )
    }
    val deviceModel = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Model",
                text =  deviceDetailsState.value.inspection.deviceModel
            )
        )
    }
    val ward = remember {
        mutableStateOf(
            DefaultTextFieldState(
                hint = "Ward",
                text =  deviceDetailsState.value.inspection.ward
            )
        )
    }

    val hospitalList = viewModel.inspectionDetailsState.value.hospitalList

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is UiEvent.UpdateInspection -> {
                    deviceName.value = deviceName.value.copy(
                        text = event.text.deviceName
                    )
                    deviceManufacturer.value = deviceManufacturer.value.copy(
                        text = event.text.deviceManufacturer
                    )
                    deviceModel.value = deviceModel.value.copy(
                        text = event.text.deviceModel
                    )
                    ward.value = ward.value.copy(
                        text = event.text.ward
                    )
                }
                is UiEvent.ShowSnackBar -> {
                    //
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ){


        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            Text(
                text = "Device",
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
            deviceName.InspectionTextField()
            deviceManufacturer.InspectionTextField()
            deviceModel.InspectionTextField()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Localization",
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
            HospitalFilterSection(
                hospitalList = hospitalList,
                hospital = hospitalList.find { (it.hospitalId == deviceDetailsState.value.inspection.hospitalId ) } ?: Hospital(),
                onHospitalChange = {
                    viewModel.onEvent(InspectionDetailsEvent.UpdateHospital(it.hospitalId))
                }
            )
            ward.InspectionTextField()

        }
    }
}

