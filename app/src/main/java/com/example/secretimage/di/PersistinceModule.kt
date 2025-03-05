package com.example.secretimage.di

import android.content.Context
import androidx.room.Room
import com.example.secretimage.localDatabase.AppDatabase
import com.example.secretimage.localDatabase.db.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class PersistinceModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "Image-database"
        ).build()

    @Provides
    fun provideUserDao(appDatabase: AppDatabase):
            ImageDao = appDatabase.imageDao()

}