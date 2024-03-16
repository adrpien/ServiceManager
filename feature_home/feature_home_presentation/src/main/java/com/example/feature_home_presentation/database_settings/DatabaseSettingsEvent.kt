package com.example.feature_home_presentation.database_settings

import java.io.InputStream

sealed class DatabaseSettingsEvent {
    data class ImportInspections(val inputStream: InputStream): DatabaseSettingsEvent()
    object SaveInspections: DatabaseSettingsEvent()

}