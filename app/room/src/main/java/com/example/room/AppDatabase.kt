package com.example.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room.db.image.ImageDao
import com.example.room.db.image.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class AppDatabase :RoomDatabase() {

    abstract fun imageDao():ImageDao
}