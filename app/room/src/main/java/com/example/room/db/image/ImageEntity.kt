package com.example.room.db.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity (
@PrimaryKey  @ColumnInfo val id:Long,
    @ColumnInfo val name:String,
    @ColumnInfo val image:String
)