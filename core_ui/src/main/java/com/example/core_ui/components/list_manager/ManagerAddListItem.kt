package com.example.core_ui.components.list_manager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ManagerAddListItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconDescription: String,
    onItemClick: () -> (Unit)
) {
    val isVisibleState = remember { MutableTransitionState(true) }

    // TODO Animations does not work
     AnimatedVisibility(
         visibleState = isVisibleState,
         modifier = Modifier.fillMaxSize(),
         enter = fadeIn(
             animationSpec = tween(1000)
         ),
         exit = fadeOut(
             animationSpec = tween(1000)
         )
     ) {
        Box(
            modifier = modifier
                .animateEnterExit(
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = onItemClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = icon, contentDescription = iconDescription)
                    }
                }
            }
        }
    }
}
