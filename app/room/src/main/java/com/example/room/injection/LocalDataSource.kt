package com.example.room.injection

import com.example.room.db.image.ImageDao
import com.example.room.source.ImageDataSourceImpl
import com.example.secretimage.domain.repository.ImageDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSource {
    @Provides
    fun provideImageDataSource(imageDao: ImageDao): ImageDataSource {
        return ImageDataSourceImpl(imageDao)
    }
}

