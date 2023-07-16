package com.example.servicemanager.feature_inspections.presentation.inspection_list.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import kotlinx.coroutines.launch

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
                backgroundColor = Color.Blue,
                contentColor = Color.White
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