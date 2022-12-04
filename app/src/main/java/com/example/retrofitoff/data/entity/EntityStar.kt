package com.example.retrofitoff.data.entity


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Stars")
data class EntityStar(
    @PrimaryKey
    @ColumnInfo(name = "starred_at")
    override val starredAt: Date,
    @Embedded(prefix = "user_")
    override val user: EntityUser,
): StarGroup {
    constructor(star: StarGroup): this(
        starredAt = star.starredAt,
        user = EntityUser(star.user)
            )
}