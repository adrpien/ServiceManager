package com.example.core_ui.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicemanager.feature_app_domain.model.Hospital

@Composable
fun HospitalSelectionSection(
    modifier: Modifier = Modifier,
    itemList: Map<Hospital, Boolean>,
    selectedItem: Hospital = itemList.toList().last().first,
    onItemChanged: (Hospital) -> Unit,
) {

    val scrollState = rememberScrollState()

    var itemListofPairs: List<Pair<Hospital, Boolean>> = itemList.toList()

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary
            ),
                MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.secondary)
            .horizontalScroll(scrollState),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            itemListofPairs.
                forEach { pair ->
                DefaultRadioButton(
                    title = pair.first.hospital,
                    selected = pair.first == selectedItem,
                    onClick = { onItemChanged(pair.first) },
                    isClickable = pair.second
                )
            }
        }
    }
}
