package com.rivaldo.core.domain.useCase

import androidx.paging.PagingData
import com.rivaldo.core.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow

interface HomeListStoryUseCase {
    fun getIsLoggedIn() : Flow<Boolean>

    fun getToken(): Flow<String>

    fun getAllStoriesPaging() : Flow<PagingData<StoryModel>>

    suspend fun logout()
}