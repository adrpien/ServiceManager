package com.example.core_ui.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.core.theme.DarkBlue
import com.example.core.theme.MediumBeige
import com.example.core.theme.LightBeige

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
            containerColor = MediumBeige,
            contentColor = DarkBlue
        ),
        border = BorderStroke(2.dp, LightBeige)
    ) {
        Text(
            text = title
        )
    }
}