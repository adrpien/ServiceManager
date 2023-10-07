package com.example.feature_app_presentation.components.inspection_state

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature_app_presentation.components.hospital_selection.SelectRadioButton
import com.example.servicemanager.feature_app_domain.model.InspectionState


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





