package com.example.core_ui.components.list_manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R

@Composable
fun ManagerListItem(
    title: String,
    description: String,
    onDeleteClick: () -> (Unit)
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Row(
            modifier = Modifier
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
                Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(id = R.string.delete_hospital))
            }

        }
    }

}