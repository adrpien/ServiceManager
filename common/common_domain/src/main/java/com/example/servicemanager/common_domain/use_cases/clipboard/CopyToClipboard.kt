package com.example.servicemanager.common_domain.use_cases.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import javax.inject.Inject

class CopyToClipboard @Inject constructor (
    private val clipboardManager: ClipboardManager
) {

    operator fun invoke(string: String) {
        val clip = ClipData.newPlainText("copied_text", string)
        clipboardManager.setPrimaryClip(clip)
    }

}