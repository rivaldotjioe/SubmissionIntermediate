package com.rivaldo.submissionintermediate.domain.interactor

import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.domain.useCase.AddStoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryInteractor(val repository: IStoriesRepository, val preferences: DataStorePreferences) : AddStoryUseCase {
    override suspend fun getToken(): Flow<String> = preferences.getToken()

    override suspend fun addNewStory(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part
    ): Flow<Resource<ResponseStandard>> = repository.addNewStory(token, description, image)
}