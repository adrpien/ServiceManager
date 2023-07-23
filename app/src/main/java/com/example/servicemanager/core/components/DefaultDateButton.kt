package com.example.servicemanager.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige

@Composable
fun DefaultDateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .background(TiemedVeryLightBeige)
    ) {
        Text(
            text = "21/07/2022"
        )
    }
}