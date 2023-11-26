package com.example.core_ui.components.other

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige

@Composable
fun OrderMonotonicityButton(
    modifier: Modifier = Modifier,
    isAscending: Boolean = true,
    onClick: () -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val animatable = remember { androidx.compose.animation.core.Animatable(1F) }


    Column(
        modifier = modifier
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBlue,
                contentColor = LightBeige
            )
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
}