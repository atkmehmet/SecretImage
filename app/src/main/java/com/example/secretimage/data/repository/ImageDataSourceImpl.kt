package com.example.secretimage.data.repository

import com.example.secretimage.localDatabase.db.ImageDao
import com.example.secretimage.localDatabase.db.ImageEntity
import com.example.secretimage.domain.repository.ImageDataSource
import com.example.secretimage.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDataSourceImpl  @Inject constructor(
private val imageDao: ImageDao
): ImageDataSource
{
    override fun getImage(): Flow<List<Image>> {
        return imageDao.getImages() // This returns Flow<List<ImageEntity>>
            .map { imageEntityList ->
                imageEntityList.map { imageEntity ->
                    imageEntity.toImage() // Convert ImageEntity to Image
                }
            }
    }

    fun ImageEntity.toImage(): Image {
        return Image(
            id = this.id,
            name = this.name,
            timestamp = this.timestamp,
            image = this.image)
    }
    override suspend fun addImage(image: Image) =
        imageDao.insertImage(
            ImageEntity(
           name = image.name,
           timestamp =   image.timestamp,
            image = image.image
        )
        )
}