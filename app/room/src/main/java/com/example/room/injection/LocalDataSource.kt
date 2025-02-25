package com.example.room.injection

import com.example.room.source.ImageDataSourceImpl
import com.example.secretimage.domain.repository.ImageDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSource {
    @Binds
    abstract fun bindUserDataSource
                (imageDataSourceImpl: ImageDataSourceImpl):
            ImageDataSource
}