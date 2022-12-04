package com.example.retrofitoff.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitoff.data.entity.EntityRepo

@Dao
interface RepoDao {
    @Query("SELECT * FROM Repo")
    suspend fun getAll(): List<EntityRepo>

    @Query("SELECT * FROM Repo WHERE id IN (:repoId)")
    suspend fun repoId(repoId: Long): EntityRepo

    @Query("SELECT * FROM Repo WHERE user IN (:user_id)")
    suspend fun userId(user_id: Long): List<EntityRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entityRepo: EntityRepo)

    @Delete
    suspend fun delete(entityRepo: EntityRepo)
}