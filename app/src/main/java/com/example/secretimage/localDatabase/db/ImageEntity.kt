package com.example.secretimage.localDatabase.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity (
    @PrimaryKey(autoGenerate = true)  @ColumnInfo val id:Long=0,
    @ColumnInfo val name:String,
    @ColumnInfo val image:String,
    @ColumnInfo val timestamp: Long

)