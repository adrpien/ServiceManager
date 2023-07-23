package com.example.servicemanager.core.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicemanager.ui.theme.TiemedMediumBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige


@Composable
fun SortRadioButton(
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
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selected) TiemedMediumBlue else TiemedVeryLightBeige,
                contentColor = if (!selected) TiemedMediumBlue else TiemedVeryLightBeige,
            ),
            border = BorderStroke(
                width = 2.dp,
                color = TiemedMediumBlue)
        ) {
            Text(text = title)
        }
    }
}