package com.example.servicemanager.feature_inspections_presentation.inspection_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.util.DateFormattingType
import com.example.core.util.Helper.Companion.getDateString
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_inspections_domain.model.Inspection

@Composable
fun InspectionListItem(
    modifier: Modifier = Modifier,
    inspection: Inspection = Inspection(),
    hospitalList: List<Hospital> = emptyList(),
    technicianList: List<Technician> = emptyList(),
    inspectionStateList: List<InspectionState> = emptyList()
) {
    Card(
        modifier = modifier
            .padding(8.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

            ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = inspection.deviceName,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = inspection.deviceManufacturer + " " + inspection.deviceModel,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "SN: " + inspection.deviceSn,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )
                Text(
                    text = "IN: " + inspection.deviceIn,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Localization: " + (hospitalList.find { inspection.hospitalId == it.hospitalId }?.hospital ?: "") + ", " +  inspection.ward,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )

            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = ("State: " + inspectionStateList.find { inspection.inspectionStateId == it.inspectionStateId }?.inspectionState),
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )
                Text(
                    text = technicianList.find { inspection.technicianId == it.technicianId }?.name ?: "",
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )

                Text(
                    text = getDateString(inspection.inspectionDate.toLong(), DateFormattingType.BackSlashStyle()),
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }

}