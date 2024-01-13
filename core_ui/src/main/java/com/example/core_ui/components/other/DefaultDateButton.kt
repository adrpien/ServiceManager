package com.example.core_ui.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.util.Helper

@Composable
fun DefaultDateButton(
    modifier: Modifier = Modifier,
    dateLong: Long,
    onClick: () -> Unit,
    enabled: Boolean,
    precedingTextSource: Int
) {
    androidx.compose.material.Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = androidx.compose.material.ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.secondary,
            disabledBackgroundColor = MaterialTheme.colorScheme.secondary
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if(enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(
            text = stringResource(precedingTextSource) + ": " + Helper.getDateString(dateLong),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}