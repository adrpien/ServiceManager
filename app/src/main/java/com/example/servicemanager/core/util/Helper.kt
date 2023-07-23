package com.example.servicemanager.core.util

import android.content.res.Resources.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
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

        fun convertToByteArray(bitmap: Bitmap): ByteArray {
            val signatureByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, signatureByteArrayOutputStream)
            return signatureByteArrayOutputStream.toByteArray()
        }

        fun convertToImageBitmap(byteArray: ByteArray): Bitmap {
            val options = BitmapFactory.Options()
            options.inMutable = true
            val bitmap = BitmapFactory.decodeByteArray(
                byteArray,
                0,
                byteArray.size,
                options
            )
            return bitmap
        }
    }
}