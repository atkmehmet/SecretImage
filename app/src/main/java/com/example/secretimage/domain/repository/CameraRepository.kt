package com.example.secretimage.domain.repository

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController

interface CameraRepository {

    suspend fun  takePhoto(
        controller: LifecycleCameraController,
        imageDataSource: ImageDataSource)

    suspend fun recordVideo(
        controller: LifecycleCameraController
    )

}