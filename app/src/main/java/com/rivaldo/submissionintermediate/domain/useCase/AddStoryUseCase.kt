package com.rivaldo.submissionintermediate.domain.useCase

import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AddStoryUseCase {
    suspend fun getToken(): Flow<String>

    suspend fun addNewStory(token: String, description: RequestBody, image: MultipartBody.Part) : Flow<Resource<ResponseStandard>>
}