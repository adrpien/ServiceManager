package com.example.servicemanager.feature_app_domain.use_cases.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.example.core.util.Resource
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CopyToClipboard @Inject constructor (
) {

    operator fun invoke(context: Context, string: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val textToCopy = string

        val clip = ClipData.newPlainText("Copied Text", string)
        clipboard.setPrimaryClip(clip)
    }

}