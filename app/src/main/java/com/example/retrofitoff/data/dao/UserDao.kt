package com.example.retrofitoff.data.dao
import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.retrofitoff.data.entity.constructor.ConstructorUser




@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): List<ConstructorUser>

    @Delete
    fun delete(entityUser: ConstructorUser)

}