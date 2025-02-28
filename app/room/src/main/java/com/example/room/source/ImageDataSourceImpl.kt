package com.example.room.source

import com.example.room.db.image.ImageDao
import com.example.room.db.image.ImageEntity
import com.example.secretimage.domain.repository.ImageDataSource
import com.example.secretimage.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageDataSourceImpl  @Inject constructor(
val imageDao: ImageDao
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
    override suspend fun addImage(imageString: String) =
        imageDao.insertImage(ImageEntity(
           
           timestamp =   image.timestamp,
            image = image.image
        ))
}