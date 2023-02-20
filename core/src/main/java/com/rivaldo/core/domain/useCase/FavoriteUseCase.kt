package com.rivaldo.core.domain.useCase
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getAllStoriesFavorite(): Flow<Resource<List<StoryModel>>>
    suspend fun insertFavorite(stories: StoryModel):Flow<Resource<String>>
    suspend fun  deleteFavorite(stories: StoryModel): Flow<Resource<String>>
    fun isRowExist(id:String): Flow<Resource<Boolean>>
}