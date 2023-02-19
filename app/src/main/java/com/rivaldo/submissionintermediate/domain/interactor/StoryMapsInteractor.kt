package com.rivaldo.submissionintermediate.domain.interactor

import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.domain.useCase.StoryMapsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class StoryMapsInteractor(val repository: IStoriesRepository, val preferences: DataStorePreferences) : StoryMapsUseCase {
    override suspend fun getToken(): Flow<String> = preferences.getToken()

    override suspend fun getListStory(token: String): Flow<Resource<List<StoryModel>>> = repository.getAllStoriesWithLocation(token)
}