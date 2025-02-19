package com.example.room.db.image

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {


    @Query("SELECT * FROM images")
    fun getImages(): Flow<List<ImageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(img:ImageEntity)

    @Query("DELETE FROM images where id=:id")
    fun deleteImage(id:Long)
}