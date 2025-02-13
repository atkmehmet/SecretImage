package com.example.secretimage.domain.repository

import androidx.camera.view.LifecycleCameraController

interface CameraRepository {

    suspend fun  takePhot(
        controller: LifecycleCameraController)

    suspend fun  recordVideo(
        controller: LifecycleCameraController
    )
}