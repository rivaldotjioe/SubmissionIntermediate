package com.rivaldo.core.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rivaldo.core.data.local.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories")
    fun getAllStoriesFavorite() : Flow<List<StoryEntity>>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(stories: StoryEntity)

    @Delete
    suspend fun deleteFavorite(stories: StoryEntity)

    @Query("SELECT EXISTS(SELECT * FROM stories WHERE id = :id)")
    fun isRowIsExist(id : String) : Boolean
}