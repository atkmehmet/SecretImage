package com.example.secretimage.data.repository

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.example.secretimage.domain.repository.CameraRepository
import javax.inject.Inject
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.video.AudioConfig
import com.example.secretimage.R
import com.example.secretimage.domain.repository.ImageDataSource
import com.example.secretimage.model.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Singleton

@Singleton
class CameraRepositoryImpl @Inject constructor(
    private val application: Application
) : CameraRepository {

    private var recoding: Recording? = null

    override suspend fun takePhoto(
        controller: LifecycleCameraController
    ) {

        controller.takePicture(
            ContextCompat.getMainExecutor(application),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply {
                        postRotate(
                            image.imageInfo.rotationDegrees.toFloat()
                        )
                    }

                    val imageBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0, 0,
                        image.width, image.height,
                        matrix, true
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                    //   imageDataSource.addImage(Image(0,"xxx01",bitmapToString(imageBitmap),0))

                    }

                }
            }
        )

    }


    fun bitmapToString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    override suspend fun recordVideo(
        controller: LifecycleCameraController
    ) {


    }


    private suspend fun savePhoto(bitmap: Bitmap) {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
           withContext(Dispatchers.IO)
           {
               val resolver: ContentResolver = application.contentResolver

               val imageCollection = MediaStore.Images.Media.getContentUri(
                   MediaStore.VOLUME_EXTERNAL_PRIMARY
               )

               val appName = application.getString(R.string.app_name)
               val timeInMillis = System.currentTimeMillis()

               val imageContentValues: ContentValues = ContentValues().apply {
                   put(
                       MediaStore.Images.Media.DISPLAY_NAME,
                       "${timeInMillis}_image" + ".jpg"
                   )
                   put(
                       MediaStore.MediaColumns.RELATIVE_PATH,
                       Environment.DIRECTORY_DCIM + "/$appName"
                   )
                   put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                   put(MediaStore.MediaColumns.DATE_TAKEN, timeInMillis)
                   put(MediaStore.MediaColumns.IS_PENDING, 1)
               }

               val imageMediaStoreUri: Uri? = resolver.insert(
                   imageCollection, imageContentValues
               )

               imageMediaStoreUri?.let { uri ->
                   try {
                       resolver.openOutputStream(uri)?.let { outputStream ->
                           bitmap.compress(
                               Bitmap.CompressFormat.JPEG, 100, outputStream
                           )
                       }

                       imageContentValues.clear()
                       imageContentValues.put(
                           MediaStore.MediaColumns.IS_PENDING, 0
                       )
                       resolver.update(
                           uri, imageContentValues, null, null
                       )

                   } catch (e: Exception) {
                       e.printStackTrace()
                       resolver.delete(uri, null, null)
                   }
               }
           }
       }
        else{
           withContext(Dispatchers.IO) {
               val timeInMillis = System.currentTimeMillis()
               val fileName = "${timeInMillis}_image.jpg"
               val appName = application.getString(R.string.app_name)

               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // API 29+
                   val resolver: ContentResolver = application.contentResolver

                   val imageCollection = MediaStore.Images.Media.getContentUri(
                       MediaStore.VOLUME_EXTERNAL_PRIMARY
                   )

                   val imageContentValues = ContentValues().apply {
                       put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                       put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/$appName")
                       put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                       put(MediaStore.MediaColumns.DATE_TAKEN, timeInMillis)
                       put(MediaStore.MediaColumns.IS_PENDING, 1)
                   }

                   val imageUri: Uri? = resolver.insert(imageCollection, imageContentValues)

                   imageUri?.let { uri ->
                       try {
                           resolver.openOutputStream(uri)?.use { outputStream ->
                               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                           }

                           imageContentValues.clear()
                           imageContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                           resolver.update(uri, imageContentValues, null, null)

                       } catch (e: Exception) {
                           e.printStackTrace()
                           resolver.delete(uri, null, null) // Delete if error occurs
                       }
                   }

               } else { // API < 29 (Android 9 and below)
                   val directory = File(
                       Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                       appName
                   )

                   if (!directory.exists()) {
                       directory.mkdirs() // Create directory if it doesn't exist
                   }

                   val imageFile = File(directory, fileName)
                   try {
                       val outputStream: OutputStream = FileOutputStream(imageFile)
                       bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                       outputStream.flush()
                       outputStream.close()
                   } catch (e: Exception) {
                       e.printStackTrace()
                   }
               }
           }
        }


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun saveVideo(file: File) {
        withContext(Dispatchers.IO) {
            val resolver: ContentResolver = application.contentResolver

            val videoCollection = MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )

            val appName = application.getString(R.string.app_name)
            val timeInMillis = System.currentTimeMillis()

            val videoContentValues: ContentValues = ContentValues().apply {
                put(
                    MediaStore.Video.Media.DISPLAY_NAME,
                    file.name
                )
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DCIM + "/$appName"
                )
                put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                put(MediaStore.MediaColumns.DATE_ADDED, timeInMillis / 1000)
                put(MediaStore.MediaColumns.DATE_MODIFIED, timeInMillis / 1000)
                put(MediaStore.MediaColumns.DATE_TAKEN, timeInMillis)
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }

            val videoMediaStoreUri: Uri? = resolver.insert(
                videoCollection, videoContentValues
            )

            videoMediaStoreUri?.let { uri ->
                try {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        resolver.openInputStream(
                            Uri.fromFile(file)
                        )?.use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }

                    videoContentValues.clear()
                    videoContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(
                        uri, videoContentValues, null, null
                    )

                } catch (e: Exception) {
                    e.printStackTrace()
                    resolver.delete(uri, null, null)
                }
            }
        }
    }

}