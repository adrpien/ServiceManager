package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun SelectRadioButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .padding(8.dp),
            onClick = onClick,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selected) Color.Blue else Color.White,
                contentColor = if (!selected) Color.Blue else Color.White,
            ),
            border = BorderStroke(
                width = 2.dp,
                color = Color.Blue)
        ) {
            Text(text = title)
        }
    }
}