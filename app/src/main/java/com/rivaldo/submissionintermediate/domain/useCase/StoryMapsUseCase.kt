package com.rivaldo.submissionintermediate.domain.useCase

import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryMapsUseCase {
    suspend fun getToken(): Flow<String>

    suspend fun getListStory(token: String) : Flow<Resource<List<StoryModel>>>
}