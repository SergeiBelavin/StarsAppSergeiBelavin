package com.example.retrofitoff.data.dao
import androidx.room.*

import com.example.retrofitoff.data.entity.EntityUser


@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    suspend fun getAll(): List<EntityUser>

    @Query("SELECT * FROM users WHERE id IN (:userId)")
    suspend fun userId(userId: Long): List<EntityUser>

    @Query("SELECT * FROM users WHERE name LIKE (:userName)")
    suspend fun userName(userName: String): EntityUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entityUser: EntityUser)

    @Delete
    suspend fun delete(entityUser: EntityUser)

}