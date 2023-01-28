package com.rivaldo.submissionintermediate.data.remote.repository

import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.data.remote.api.RemoteDataSource
import com.rivaldo.submissionintermediate.data.remote.model.ListStoryItem
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.utils.DataMapper.toStoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StoriesRepository(val remoteDataSource: RemoteDataSource) : IStoriesRepository {
    override suspend fun getAllStories(token: String): Flow<Resource<List<StoryModel>>> {
        return flow {
            try {
                remoteDataSource.getAllStories(token = token).collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            response.data?.listStory.let {
                                if (it != null) {
                                    emit(Resource.Success((it as List<ListStoryItem>).toStoryModel()))
                                }
                            }
                        }
                        is ApiResponse.Error -> {
                            emit(Resource.Error(message = response.message.toString()))
                        }
                        is ApiResponse.Loading -> {
                            emit(Resource.Loading(data = null))
                        }
                    }
                }
            } catch (e: Exception){
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }
}