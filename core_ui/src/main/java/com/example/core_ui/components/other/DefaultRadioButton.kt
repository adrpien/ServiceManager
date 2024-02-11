package com.example.core_ui.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.google.android.material.color.MaterialColors


@Composable
fun DefaultRadioButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClickable: Boolean
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .padding(4.dp)
                .alpha(if(isClickable) 1.0f else 0.5f),
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                disabledContainerColor = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if(selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
            ),
            enabled = isClickable
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}