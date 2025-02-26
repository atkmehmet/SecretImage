package com.example.secretimage.domain.repository

import com.example.secretimage.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageDataSource {

fun getImage():Flow<List<Image>>

suspend fun addImage(imageString: String)
}