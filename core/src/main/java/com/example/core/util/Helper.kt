package com.example.core.util

import android.content.Context
import android.content.res.Resources.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class Helper {

    companion object {

        // Changing millis into String format
        fun getDateString(millis: Long, formatingType: DateFormattingType = DateFormattingType.BackSlashStyle()): String{
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

        fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                return bitmap
            } catch (e: Exception) {
                return null
            }
        }

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

        fun drawableToByteArray(drawable: Drawable): ByteArray {
            // Convert Drawable to Bitmap
            val bitmap = drawableToBitmap(drawable)

            // Convert Bitmap to ByteArray
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }

        fun drawableToBitmap(drawable: Drawable): Bitmap {
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

        fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val byteArrayOutputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var length: Int
                if (inputStream != null) {
                    while (inputStream.read(buffer).also { length = it } > 0) {
                        byteArrayOutputStream.write(buffer, 0, length)
                    }
                    inputStream.close()
                    return byteArrayOutputStream.toByteArray()
                } else {
                    return null

                }
            } catch (e: Exception) {
                return null
            }
        }
    }
}