package com.example.core.util

sealed class DateFormattingTypes(val formatting: String) {

    data class BackSlashStyle(val value: String = "dd/MM/YYYY"): DateFormattingTypes(value)
    data class AmericanStyle(val value: String = "YYYY/MM/dd"): DateFormattingTypes(value)
    data class DashStyle(val value: String = "dd-MM-YYYY"): DateFormattingTypes(value)

    companion object {
        fun getDateFormattingTypeWithString(string: String): DateFormattingTypes {
            when(string) {
                DashStyle().value -> {
                    return DashStyle()
                }
                AmericanStyle().value -> {
                    return AmericanStyle()
                }
                AmericanStyle().value -> {
                    return AmericanStyle()
                }
                else -> {
                    return DashStyle()
                }
            }
        }
    }

}


