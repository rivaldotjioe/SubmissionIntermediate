package com.rivaldo.submissionintermediate.domain.useCase

import androidx.paging.PagingData
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow

interface HomeListStoryUseCase {
    fun getIsLoggedIn() : Flow<Boolean>

    fun getToken(): Flow<String>

    fun getAllStoriesPaging() : Flow<PagingData<StoryModel>>

    suspend fun logout()
}