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
    private val clipboardManager: ClipboardManager
) {

    operator fun invoke(string: String) {
        val clip = ClipData.newPlainText("copied_text", string)
        clipboardManager.setPrimaryClip(clip)
    }

}