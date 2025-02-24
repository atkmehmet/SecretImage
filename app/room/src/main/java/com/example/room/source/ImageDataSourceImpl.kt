package com.example.room.source

import com.example.room.db.image.ImageDao
import com.example.secretimage.domain.repository.ImageDataSource
import com.example.secretimage.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageDataSourceImpl  @Inject constructor(
val imageDao: ImageDao
): ImageDataSource
{

    override fun getImage(): Flow<List<Image>> {
        TODO("Not yet implemented")
    }

    override suspend fun addImage(image: Image) {
        TODO("Not yet implemented")
    }
}