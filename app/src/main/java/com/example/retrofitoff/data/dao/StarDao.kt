package com.example.retrofitoff.data.dao


import android.content.ClipData.Item
import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitoff.data.entity.constructor.ConstructorStar

@Dao
interface StarDao {
    @Query("SELECT * FROM stars")
    fun getAll(): List<ConstructorStar>

    @Delete
    fun delete(tableStarLocal: ConstructorStar)
}