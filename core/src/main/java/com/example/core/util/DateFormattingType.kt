package com.example.core.util

sealed class DateFormattingType(val formatting: String) {

    data class BackSlashStyle(val value: String = "dd/MM/YYYY"): DateFormattingType(value)
    data class AmericanStyle(val value: String = "YYYY/MM/dd"): DateFormattingType(value)
    data class DashStyle(val value: String = "dd-MM-YYYY"): DateFormattingType(value)

    companion object {
        fun getDateFormattingTypeWithString(string: String): DateFormattingType {
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


