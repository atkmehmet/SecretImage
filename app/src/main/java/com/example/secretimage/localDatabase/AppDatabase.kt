package com.example.secretimage.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.secretimage.localDatabase.db.ImageDao
import com.example.secretimage.localDatabase.db.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class AppDatabase :RoomDatabase() {

    abstract fun imageDao(): ImageDao
}