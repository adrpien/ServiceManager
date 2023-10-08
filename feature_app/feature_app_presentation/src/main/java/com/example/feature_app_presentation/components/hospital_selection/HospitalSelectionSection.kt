package com.example.feature_app_presentation.components.hospital_selection

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicemanager.feature_app_domain.model.Hospital

@Composable
fun HospitalSelectionSection(
    modifier: Modifier = Modifier,
    hospitalList: List<Hospital>,
    hospital: Hospital,
    onHospitalChange: (Hospital) -> Unit,
    enabled: Boolean
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            hospitalList.forEach { item ->
                SelectRadioButton(
                    title = item.hospital,
                    selected = item.hospitalId == hospital.hospitalId,
                    onClick = { onHospitalChange(item) },
                    isClickable = enabled
                )
            }
        }
    }
}





