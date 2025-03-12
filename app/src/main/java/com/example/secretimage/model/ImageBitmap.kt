package com.example.secretimage.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.File

data class ImageBitmap (
    val id:Long,
    val name:String,
    val image:Bitmap,
    val timestamp: Long
)

fun Image.toImageBitmap(): ImageBitmap? {
    val bitmap = image.toBitmap() ?: return null
    return ImageBitmap(id, name, bitmap, timestamp)
}

fun String.toBitmap(): Bitmap? {
    return try {
        if (this.startsWith("/")) { // Assume it's a file path
            BitmapFactory.decodeFile(File(this).absolutePath)
        } else { // Assume it's Base64
            val decodedBytes = Base64.decode(this, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}