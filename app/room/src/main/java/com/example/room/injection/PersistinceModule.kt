package com.example.room.injection

import android.content.Context
import androidx.room.Room
import com.example.room.db.AppDatabase
import com.example.room.db.image.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


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