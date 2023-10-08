package com.example.feature_app_presentation.components.technician

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature_app_presentation.components.hospital_selection.SelectRadioButton
import com.example.servicemanager.feature_app_domain.model.Technician

@Composable
fun TechnicianSelectionSection(
    modifier: Modifier = Modifier,
    technicianList: List<Technician>,
    technician: Technician,
    onTechnicianChange: (Technician) -> Unit,
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
            technicianList.forEach { item ->
                SelectRadioButton(
                    title = item.name,
                    selected = item.technicianId == technician.technicianId,
                    onClick = { onTechnicianChange(item) },
                isClickable = enabled
                )
            }
        }
    }
}





