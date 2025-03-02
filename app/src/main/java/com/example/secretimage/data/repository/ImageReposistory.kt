package com.example.secretimage.data.repository

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import com.example.secretimage.domain.repository.ImageDataSource
import javax.inject.Inject

import android.util.Base64
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.example.secretimage.model.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream

class ImageReposistory @Inject constructor(
  private val imageDataSource: ImageDataSource,
    private val application: Application
) {

    fun saveImage(bitmap:Bitmap){
        val imageString :String = bitmapToString(bitmap)
         runBlocking {
           imageDataSource.addImage(Image(0,"xx1",imageString,89))
         }
    }


    fun bitmapToString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}