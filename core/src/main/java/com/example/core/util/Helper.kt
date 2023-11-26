package com.example.core.util

import android.content.Context
import android.content.res.Resources.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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

        fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
            val signatureByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, signatureByteArrayOutputStream)
            return signatureByteArrayOutputStream.toByteArray()
        }

        fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
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

        fun drawableToByteArray(context: Context, drawable: Drawable): ByteArray {
            // Convert Drawable to Bitmap
            val bitmap = drawableToBitmap(drawable)

            // Convert Bitmap to ByteArray
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }

        private fun drawableToBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }

            val bitmap: Bitmap

            if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                // Single color drawable, create an ARGB bitmap to draw it
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            } else {
                // Regular drawable with a size, create a bitmap of that size
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)

            return bitmap
        }

    }
}