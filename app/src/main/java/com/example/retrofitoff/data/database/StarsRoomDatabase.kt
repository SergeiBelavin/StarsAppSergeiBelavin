package com.example.retrofitoff.data.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.retrofitoff.data.dao.RepoDao
import com.example.retrofitoff.data.dao.StarDao
import com.example.retrofitoff.data.dao.UserDao
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import com.example.retrofitoff.data.entity.constructor.ConstructorStar
import com.example.retrofitoff.data.entity.constructor.ConstructorUser
import com.example.retrofitoff.data.repository.DateConverter

@TypeConverters(DateConverter::class)
@Database(
    entities = [ConstructorUser::class, ConstructorRepo::class, ConstructorStar::class],
    version = 1
)

abstract class StarsRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
    abstract fun starDao(): StarDao

    companion object {
        fun getDb(context: Context): StarsRoomDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                StarsRoomDatabase::class.java,
                "database").build()
        }
    }

}