package com.rivaldo.core.domain.useCase

import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow

interface DetailUseCase {

   suspend fun isFavorite(id: String): Flow<Resource<Boolean>>

   suspend fun addToFavorite(story: StoryModel): Flow<Resource<String>>

   suspend fun deleteFavorite(story: StoryModel): Flow<Resource<String>>
}