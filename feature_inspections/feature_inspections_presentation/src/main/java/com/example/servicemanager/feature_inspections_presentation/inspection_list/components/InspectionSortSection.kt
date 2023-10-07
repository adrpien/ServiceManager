package com.example.servicemanager.feature_inspections_presentation.inspection_list.components
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature_app_presentation.components.other.OrderMonotonicityButton
import com.example.feature_app_presentation.components.hospital_filter.SortRadioButton
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderMonotonicity
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderType

@Composable
fun InspectionSortSection(
    modifier: Modifier = Modifier,
    inspectionOrderType: InspectionOrderType = InspectionOrderType.Date(InspectionOrderMonotonicity.Descending),
    onOrderChange: (InspectionOrderType) -> Unit,
    onToggleMonotonicity: (InspectionOrderType) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start,

            ) {
                SortRadioButton(
                    title = "Hospital",
                    selected = inspectionOrderType is InspectionOrderType.Hospital,
                    onClick = { onOrderChange(InspectionOrderType.Hospital(inspectionOrderType.orderMonotonicity)) })
                SortRadioButton(
                    title = "State",
                    selected = inspectionOrderType is InspectionOrderType.State,
                    onClick = { onOrderChange(InspectionOrderType.State(inspectionOrderType.orderMonotonicity)) })
                SortRadioButton(
                    title = "Date",
                    selected = inspectionOrderType is InspectionOrderType.Date,
                    onClick = { onOrderChange(InspectionOrderType.Date(inspectionOrderType.orderMonotonicity)) })

            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OrderMonotonicityButton(
                    isAscending = inspectionOrderType.orderMonotonicity is InspectionOrderMonotonicity.Ascending,
                    onClick = { onToggleMonotonicity(inspectionOrderType.toggleOrderMonotonicity()) }
                )
            }
        }

    }
}





