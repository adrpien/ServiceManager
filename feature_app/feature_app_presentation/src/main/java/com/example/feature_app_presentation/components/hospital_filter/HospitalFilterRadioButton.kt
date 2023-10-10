package com.example.feature_app_presentation.components.hospital_filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.theme.TiemedLightBlue
import com.example.core.theme.TiemedLightBeige


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
                containerColor = if (selected) TiemedLightBlue else TiemedLightBeige,
                contentColor = if (!selected) TiemedLightBlue else TiemedLightBeige,
            ),
            border = BorderStroke(
                width = 2.dp,
                color = TiemedLightBlue
            )
        ) {
            Text(text = title)
        }
    }
}