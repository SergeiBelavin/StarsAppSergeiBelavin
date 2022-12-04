package com.example.retrofitoff.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo

@Dao
interface RepoDao {
    @Query("SELECT * FROM repo")
     fun getAll(): List<ConstructorRepo>

    @Delete
    suspend fun delete(entityRepo: ConstructorRepo)
}