package com.example.servicemanager.feature_inspections.presentation.inspection_list.components

import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OrderMonotonicityButton(
    modifier: Modifier = Modifier,
    isAscending: Boolean = true,
    onClick: () -> Unit,
) {

    Button(
        onClick = { onClick },
        modifier = modifier
    ) {
        if(isAscending){
            Icon(
                imageVector = Icons.Default.ArrowCircleUp,
                contentDescription = "Ascending"
            )
        } else {
            Icon(
                imageVector = Icons.Default.ArrowCircleDown,
                contentDescription = "Descending"
            )
        }
    }

}