package com.example.servicemanager.feature_app_data.local.local_image_source

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_data.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class LocalImageSource @Inject constructor(val context: Context) {

    suspend fun savePhotoLocally(byteArray: ByteArray, filename: String) {
        return withContext(Dispatchers.IO) {
            try {
                val fileName = "$filename.jpg"
                val storageDir: File? = context.getExternalFilesDir(Environment.getExternalStorageDirectory().toString() + "/ServiceManager")
                val photoFile = File(storageDir, fileName)
                if (photoFile.exists()) {
                    photoFile.delete()
                }
                val outputStream: OutputStream = FileOutputStream(photoFile.path)
                outputStream.write(byteArray)
                outputStream.close()
            } catch (e: Exception) {

            }
        }
    }

    suspend fun getPhotoWithUri(uri: Uri): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                val contentResolver: ContentResolver = context.contentResolver
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                val byteArrayOutputStream = ByteArrayOutputStream()

                inputStream?.use { input ->
                    val buffer = ByteArray(4 * 1024)
                    var read: Int
                    while (input.read(buffer).also { read = it } != -1) {
                        byteArrayOutputStream.write(buffer, 0, read)
                    }
                    byteArrayOutputStream.flush()
                }

                byteArrayOutputStream.toByteArray()
            } catch (e: Exception) {
                null
            }
        }
    }

}