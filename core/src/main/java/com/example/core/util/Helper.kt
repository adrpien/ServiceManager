package com.example.core.util

import android.content.res.Resources.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Helper {

    companion object {

        // Changing millis into String format
        fun getDateString(millis: Long, formatingType: DateFormattingTypes = DateFormattingTypes.BackSlashStyle()): String{
            var dateString: String
            val date = Date(millis)
            val formatter = SimpleDateFormat(formatingType.formatting)
            dateString = formatter.format(date)
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

        fun convertToBitmap(byteArray: ByteArray): Bitmap {
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