package com.example.core_ui.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.servicemanager.ui.theme.TiemedLightBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick =  onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = TiemedVeryLightBeige,
            contentColor = TiemedLightBlue
        ),
        border = BorderStroke(2.dp, TiemedLightBlue)
    ) {
        Text(
            text = title
        )
    }
}