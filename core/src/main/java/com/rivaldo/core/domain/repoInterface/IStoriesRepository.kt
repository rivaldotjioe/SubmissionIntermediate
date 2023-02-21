package com.rivaldo.core.domain.repoInterface

import androidx.paging.PagingData
import com.rivaldo.core.data.remote.model.ResponseStandard
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StandardModel
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.core.ui.StoryPagingSource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IStoriesRepository {
    suspend fun getAllStories(token: String): Flow<Resource<List<StoryModel>>>

    fun getPagingSource(): StoryPagingSource

    fun getAllStoriesPaging(): Flow<PagingData<StoryModel>>

    suspend fun getAllStoriesWithLocation(token: String) : Flow<Resource<List<StoryModel>>>

    suspend fun addNewStory(token: String, description: RequestBody, image: MultipartBody.Part): Flow<Resource<StandardModel>>
}