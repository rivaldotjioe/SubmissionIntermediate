package com.rivaldo.core.domain.repoInterface


import com.rivaldo.core.data.local.entity.StoryEntity
import com.rivaldo.core.data.remote.model.Story
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    fun getAllStoriesFavorite(): Flow<Resource<List<StoryModel>>>

    suspend fun insertFavorite(story: StoryModel): Flow<Resource<String>>

    suspend fun deleteFavorite(story: StoryModel): Flow<Resource<String>>

    fun isRowExist(id:String): Flow<Resource<Boolean>>
}