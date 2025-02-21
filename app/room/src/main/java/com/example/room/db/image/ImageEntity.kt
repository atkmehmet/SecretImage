package com.example.room.db.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "images")
data class ImageEntity (
    @PrimaryKey(autoGenerate = true)  @ColumnInfo val id:Long=0,
    @ColumnInfo val name:String,
    @ColumnInfo val image:String,
    @ColumnInfo val timestamp: Long

)