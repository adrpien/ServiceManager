package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicemanager.core.compose.components.SelectRadioButton
import com.example.servicemanager.feature_app.domain.model.EstState
import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_app.domain.model.InspectionState

@Composable
fun InspectionStateSelectionSection(
    modifier: Modifier = Modifier,
    inspectionStateList: List<InspectionState>,
    inspectionState: InspectionState,
    onInspectionStateChange: (InspectionState) -> Unit,
    isClickable: Boolean
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
            inspectionStateList.forEach { item ->
                SelectRadioButton(
                    title = item.inspectionState,
                    selected = item.inspectionStateId == inspectionState.inspectionStateId,
                    onClick = { onInspectionStateChange(item) },
                    isClickable = isClickable
                )
            }
        }
    }
}





