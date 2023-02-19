package com.rivaldo.core.domain.interactor

import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.core.domain.repoInterface.IStoriesRepository
import com.rivaldo.core.domain.useCase.StoryMapsUseCase
import kotlinx.coroutines.flow.Flow

class StoryMapsInteractor(val repository: IStoriesRepository, val preferences: DataStorePreferences) :
    StoryMapsUseCase {
    override suspend fun getToken(): Flow<String> = preferences.getToken()

    override suspend fun getListStory(token: String): Flow<Resource<List<StoryModel>>> = repository.getAllStoriesWithLocation(token)
}