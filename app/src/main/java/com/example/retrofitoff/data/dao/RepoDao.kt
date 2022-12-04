package com.example.retrofitoff.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo

@Dao
interface RepoDao {
    @Query("SELECT * FROM Repo")
    suspend fun getAll(): List<ConstructorRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entityRepo: ConstructorRepo)

    @Delete
    suspend fun delete(entityRepo: ConstructorRepo)
}