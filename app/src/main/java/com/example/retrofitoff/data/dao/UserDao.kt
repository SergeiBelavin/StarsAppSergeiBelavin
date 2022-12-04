package com.example.retrofitoff.data.dao
import androidx.room.*

import com.example.retrofitoff.data.entity.constructor.ConstructorUser


@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    suspend fun getAll(): List<ConstructorUser>

    @Query("SELECT * FROM users WHERE id IN (:userId)")
    suspend fun userId(userId: Long): List<ConstructorUser>

    @Query("SELECT * FROM users WHERE name LIKE (:userName)")
    suspend fun userName(userName: String): ConstructorUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entityUser: ConstructorUser)

    @Delete
    suspend fun delete(entityUser: ConstructorUser)

}