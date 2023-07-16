package com.example.servicemanager.core.util

import android.content.res.Resources.*
import java.util.*

class Helper {

    companion object {

        // Changing millis into String format
        fun getDateString(millis: Long): String{
            var dateString = ""
            if(millis != 0L) {
                val date: Calendar = Calendar.getInstance()
                date.timeInMillis = millis

                val day = date.get(Calendar.DAY_OF_MONTH)
                val month = date.get(Calendar.MONTH) + 1
                val year = date.get(Calendar.YEAR)

                val dayString = if( day < 10) { "0" + day.toString() }
                else { day.toString() }

                val monthString = if(month < 10)
                { "0" + month.toString() }
                else { month.toString() }

                val yearString = year.toString()
                dateString = "$dayString/$monthString/$yearString"
                return dateString
            }
            return dateString
        }

        val Int.toDp: Int
            get() = (this / getSystem().displayMetrics.density).toInt()
        val Int.toPx: Int
            get() = (this * getSystem().displayMetrics.density).toInt()
    }

}