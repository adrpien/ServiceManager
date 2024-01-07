package com.example.caching_domain.use_cases

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class SavePhotoLocally @Inject constructor (
    private val context: Context
) {
    suspend operator fun invoke(byteArray: ByteArray): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(
            Date()
        )
        val fileName = "IMG_$timeStamp.jpg"
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val photoFile = File(storageDir, fileName)
        saveByteArrayToFile(byteArray, photoFile)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            photoFile
        )
    }
    private fun saveByteArrayToFile(byteArray: ByteArray, file: File) {
        val outputStream: OutputStream = FileOutputStream(file)
        outputStream.write(byteArray)
        outputStream.close()
    }
}