package com.example.retrofitoff.data.dao

import androidx.room.*

import com.example.retrofitoff.data.entity.constructor.ConstructorStar

@Dao
interface StarDao {
    @Query("SELECT * FROM Stars")
    suspend fun getAll(): List<ConstructorStar>

    @Query("SELECT * FROM Stars WHERE starred_at LIKE :starredAt")
    suspend fun starredAt(starredAt: Long): ConstructorStar

    @Query("SELECT * FROM Stars WHERE user_name LIKE :userName")
    suspend fun userName(userName: String): List<ConstructorStar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg tableStarLocal: ConstructorStar)

    @Delete
    suspend fun delete(tableStarLocal: ConstructorStar)
}