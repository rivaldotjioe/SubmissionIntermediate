package com.rivaldo.submissionintermediate.domain.repoInterface

import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import kotlinx.coroutines.flow.Flow

interface IStoriesRepository {
    suspend fun getAllStories(token: String): Flow<Resource<List<StoryModel>>>
}