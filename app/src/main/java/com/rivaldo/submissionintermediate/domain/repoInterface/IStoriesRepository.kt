package com.rivaldo.submissionintermediate.domain.repoInterface

import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IStoriesRepository {
    suspend fun getAllStories(token: String): Flow<Resource<List<StoryModel>>>

    suspend fun addNewStory(token: String, description: RequestBody, image: MultipartBody.Part): Flow<Resource<ResponseStandard>>
}