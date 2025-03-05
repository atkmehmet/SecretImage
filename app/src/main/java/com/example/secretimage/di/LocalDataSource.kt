package com.example.secretimage.di

import com.example.secretimage.localDatabase.db.ImageDao
import com.example.secretimage.data.repository.ImageDataSourceImpl
import com.example.secretimage.domain.repository.ImageDataSource
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

