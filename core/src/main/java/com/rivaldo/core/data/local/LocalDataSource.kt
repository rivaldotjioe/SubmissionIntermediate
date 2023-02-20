package com.rivaldo.core.data.local

import com.rivaldo.core.data.local.entity.StoryEntity
import com.rivaldo.core.data.local.room.StoryDao

class LocalDataSource(private val storyDao: StoryDao) {
    fun getAllStoriesFavorite() = storyDao.getAllStoriesFavorite()
    suspend fun insertFavorite(story: StoryEntity) = storyDao.insertFavorite(story)
    suspend fun deleteFavorite(story: StoryEntity) = storyDao.deleteFavorite(story)
    fun isRowExist(id:String) = storyDao.isRowIsExist(id)
}