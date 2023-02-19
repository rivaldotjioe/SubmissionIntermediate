package com.rivaldo.core.domain.useCase

import com.rivaldo.core.data.remote.model.ResponseStandard
import com.rivaldo.core.domain.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AddStoryUseCase {
    suspend fun getToken(): Flow<String>

    suspend fun addNewStory(token: String, description: RequestBody, image: MultipartBody.Part) : Flow<Resource<ResponseStandard>>
}