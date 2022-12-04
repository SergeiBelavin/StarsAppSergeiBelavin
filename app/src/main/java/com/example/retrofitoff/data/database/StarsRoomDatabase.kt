package com.example.retrofitoff.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.retrofitoff.data.dao.RepoDao
import com.example.retrofitoff.data.dao.StarDao
import com.example.retrofitoff.data.dao.UserDao
import com.example.retrofitoff.data.entity.EntityRepo
import com.example.retrofitoff.data.entity.EntityStar
import com.example.retrofitoff.data.entity.EntityUser

@Database(
    entities = [EntityUser::class, EntityRepo::class, EntityStar::class],
    version = 3
)
abstract class StarsRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
    abstract fun StarDao(): StarDao
}