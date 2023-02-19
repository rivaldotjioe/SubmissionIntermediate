package com.rivaldo.core.domain.useCase

import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow

interface StoryMapsUseCase {
    suspend fun getToken(): Flow<String>

    suspend fun getListStory(token: String) : Flow<Resource<List<StoryModel>>>
}