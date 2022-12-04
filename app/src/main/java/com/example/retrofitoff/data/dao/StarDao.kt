package com.example.retrofitoff.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.retrofitoff.data.entity.EntityStar
@Dao
interface StarDao {
    @Query("SELECT * FROM stars")
    suspend fun getAll(): List<EntityStar>

    @Query("SELECT * FROM stars WHERE starred_at LIKE :starredAt")
    suspend fun starredAt(starredAt: Long): EntityStar

    @Query("SELECT * FROM stars WHERE user_name LIKE :userName")
    suspend fun userName(userName: String): List<EntityStar>
}