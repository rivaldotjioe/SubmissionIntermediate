package com.rivaldo.core.domain.interactor

import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.core.data.remote.model.ResponseStandard
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StandardModel
import com.rivaldo.core.domain.repoInterface.IStoriesRepository
import com.rivaldo.core.domain.useCase.AddStoryUseCase
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryInteractor(val repository: IStoriesRepository, val preferences: DataStorePreferences) :
    AddStoryUseCase {
    override suspend fun getToken(): Flow<String> = preferences.getToken()

    override suspend fun addNewStory(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part
    ): Flow<Resource<StandardModel>> = repository.addNewStory(token, description, image)
}