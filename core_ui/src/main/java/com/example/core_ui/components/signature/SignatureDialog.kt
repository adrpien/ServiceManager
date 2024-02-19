package com.example.core_ui.components.signature

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.core_ui.R
import com.example.core_ui.components.signature.SignatureArea
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun SignatureDialog(
    signatureDialogState: MaterialDialogState,
    onSignatureStateUpdate: (Bitmap) -> Unit,
    ) {

    MaterialDialog(
        dialogState = signatureDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        buttons = {
            positiveButton(
                text = stringResource(R.string.confirm),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )
            negativeButton(
                text = stringResource(R.string.cancel),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            contentAlignment = Alignment.Center
            ) {
            SignatureArea(
                updateImageBitmap = {
                    onSignatureStateUpdate(it)
                }
            )
        }
    }

}