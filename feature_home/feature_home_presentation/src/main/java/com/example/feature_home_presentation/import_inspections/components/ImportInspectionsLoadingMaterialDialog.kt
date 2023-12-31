package com.example.feature_home_presentation.import_inspections.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState

@Composable
fun ImportInspectionsLoadingMaterialDialog(
    dialogState: MaterialDialogState,
    state: MutableState<DefaultInspectionsLoadingDialogState>
) {
    MaterialDialog(
        dialogState = dialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = MaterialTheme.colorScheme.primary,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            val text = if(state.value.counter != null) {
                state.value.text + ": " + state.value.counter
            } else {
                state.value.text
            }
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSecondary
                )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = state.value.progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.secondary),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}