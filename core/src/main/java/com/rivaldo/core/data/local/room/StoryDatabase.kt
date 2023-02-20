package com.rivaldo.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivaldo.core.data.local.entity.StoryEntity

@Database(
    entities = [StoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao() : StoryDao
}