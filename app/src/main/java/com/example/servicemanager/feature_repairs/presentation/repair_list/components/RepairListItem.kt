package com.example.servicemanager.feature_repairs.presentation.repair_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_app.domain.model.RepairState
import com.example.servicemanager.feature_app.domain.model.Technician
import com.example.servicemanager.feature_repairs.domain.model.Repair
import com.example.servicemanager.ui.theme.TiemedLightBeige
import com.example.servicemanager.ui.theme.TiemedLightBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige

@Preview
@Composable
fun RepairListItem(
    modifier: Modifier = Modifier,
    repair: Repair = Repair(),
    hospitalList: List<Hospital> = emptyList(),
    technicianList: List<Technician> = emptyList(),
    repairStateList: List<RepairState> = emptyList()
) {
    Card(
        modifier = modifier
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = TiemedVeryLightBeige
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
                    text = repair.deviceName,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = TiemedLightBlue,
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
                    text = repair.deviceManufacturer + " " + repair.deviceModel,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colors.onBackground,
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
                    text = "SN: " + repair.deviceSn,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )
                Text(
                    text = "IN: " + repair.deviceIn,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colors.onBackground,
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
                    text = "Localization: " + (hospitalList.find { repair.hospitalId == it.hospitalId }?.hospital ?: "") + ", " +  repair.ward,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colors.onBackground,
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
                    text = "State: " + repairStateList.find { repair.repairStateId == it.repairStateId }?.repairState ?: "",
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)

                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }

}