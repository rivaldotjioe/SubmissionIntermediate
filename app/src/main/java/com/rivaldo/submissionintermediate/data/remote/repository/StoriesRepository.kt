package com.rivaldo.submissionintermediate.data.remote.repository

import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.data.remote.api.RemoteDataSource
import com.rivaldo.submissionintermediate.data.remote.model.ListStoryItem
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.utils.DataMapper.toStoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

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
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

    override suspend fun addNewStory(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part
    ): Flow<Resource<ResponseStandard>> {
        return flow {
            try {
                remoteDataSource.addNewStories(
                    token = token,
                    description = description,
                    image = image
                ).collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            response.data?.let {
                                emit(Resource.Success(it))
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
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }


}