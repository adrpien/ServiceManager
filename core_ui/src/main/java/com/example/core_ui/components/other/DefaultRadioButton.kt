package com.example.core_ui.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige
import com.example.core.theme.VeryLightBlue


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
                .padding(8.dp),
            onClick = onClick,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selected) LightBlue else LightBeige,
                contentColor = if (!selected) LightBlue else LightBeige,
                disabledBackgroundColor = if(selected) VeryLightBlue else LightBeige,
                disabledContentColor = if (selected) LightBeige else VeryLightBlue
            ),
            border = BorderStroke(
                width = 2.dp,
                color = LightBlue
            ),
            enabled = isClickable
        ) {
            Text(text = title)
        }
    }
}