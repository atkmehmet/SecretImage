package com.example.room.dataRepository

import kotlinx.coroutines.flow.Flow

interface ImageDataSource {

fun getImage():Flow<List<Image>>

suspend fun addImage(image:Image)
}