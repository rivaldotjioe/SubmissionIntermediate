package com.rivaldo.submissionintermediate.domain.repoInterface

import androidx.paging.PagingData
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import com.rivaldo.submissionintermediate.ui.main.StoryPagingSource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IStoriesRepository {
    suspend fun getAllStories(token: String): Flow<Resource<List<StoryModel>>>

    fun getPagingSource(): StoryPagingSource

    fun getAllStoriesPaging(): Flow<PagingData<StoryModel>>

    suspend fun getAllStoriesWithLocation(token: String) : Flow<Resource<List<StoryModel>>>

    suspend fun addNewStory(token: String, description: RequestBody, image: MultipartBody.Part): Flow<Resource<ResponseStandard>>
}