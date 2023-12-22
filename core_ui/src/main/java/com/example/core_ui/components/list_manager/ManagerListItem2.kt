package com.example.core_ui.components.list_manager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ManagerListItem2(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: ImageVector,
    iconDescription: String,
    onDeleteClick: () -> (Unit)
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(
            animationSpec = tween(
                500,
                delayMillis = 250,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = fadeOut()
    ) {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .animateEnterExit(
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .requiredHeight(100.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .padding(start = 8.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = description,
                                fontSize = 8.sp,
                                color = MaterialTheme.colorScheme.onSecondary,
                                textAlign = TextAlign.Start
                            )
                        }
                        IconButton(
                            onClick = onDeleteClick
                        ) {
                            Icon(imageVector = icon, contentDescription = iconDescription)
                        }
                    }
                }
            }
        }
    }
}