package com.example.core.util

sealed class DateFormattingTypes(val formatting: String) {
    data class backSlashStyle(val value: String = "dd/MM/YYYY"): DateFormattingTypes(value)
    data class americanStyle(val value: String = "YYYY/MM/dd"): DateFormattingTypes(value)
    data class dashStyle(val value: String = "dd-MM-YYYY"): DateFormattingTypes(value)

}
