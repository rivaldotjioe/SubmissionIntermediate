package com.rivaldo.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.core.data.remote.api.RemoteDataSource
import com.rivaldo.core.data.remote.model.ListStoryItem
import com.rivaldo.core.data.remote.model.ResponseStandard
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StandardModel
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.core.domain.repoInterface.IStoriesRepository
import com.rivaldo.core.ui.StoryPagingSource
import com.rivaldo.core.utils.DataMapper.toStandardModel
import com.rivaldo.core.utils.DataMapper.toStoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoriesRepository(val remoteDataSource: RemoteDataSource, val preferences: DataStorePreferences) :
    IStoriesRepository {
    override suspend fun getAllStories(token: String): Flow<Resource<List<StoryModel>>> {
        return flow {
            try {
                remoteDataSource.getAllStories(token = token).collect { response ->
                    when (response) {
                        is com.rivaldo.core.data.remote.ApiResponse.Success -> {
                            response.data?.listStory.let {
                                if (it != null) {
                                    emit(Resource.Success((it as List<ListStoryItem>).toStoryModel()))
                                }

                            }
                        }
                        is com.rivaldo.core.data.remote.ApiResponse.Error -> {
                            emit(Resource.Error(message = response.message.toString()))
                        }
                        is com.rivaldo.core.data.remote.ApiResponse.Loading -> {
                            emit(Resource.Loading(data = null))
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

    override fun getPagingSource(): StoryPagingSource = StoryPagingSource(remoteDataSource = remoteDataSource, preferences = preferences)

    override fun getAllStoriesPaging(): Flow<PagingData<StoryModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(remoteDataSource, preferences)
            }
        ).flow
    }


    override suspend fun getAllStoriesWithLocation(token: String): Flow<Resource<List<StoryModel>>> {
        return flow {
            try {
                remoteDataSource.getAllStoriesWithLocation(token = token).collect { response ->
                    when (response) {
                        is com.rivaldo.core.data.remote.ApiResponse.Success -> {
                            response.data?.listStory.let {
                                if (it != null) {
                                    emit(Resource.Success((it as List<ListStoryItem>).toStoryModel()))
                                }
                            }
                        }
                        is com.rivaldo.core.data.remote.ApiResponse.Error -> {
                            emit(Resource.Error(message = response.message.toString()))
                        }
                        is com.rivaldo.core.data.remote.ApiResponse.Loading -> {
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
    ): Flow<Resource<StandardModel>> {
        return flow {
            try {
                remoteDataSource.addNewStories(
                    token = token,
                    description = description,
                    image = image
                ).collect { response ->
                    when (response) {
                        is com.rivaldo.core.data.remote.ApiResponse.Success -> {
                            response.data?.let {
                                emit(Resource.Success(it.toStandardModel()))
                            }
                        }
                        is com.rivaldo.core.data.remote.ApiResponse.Error -> {
                            emit(Resource.Error(message = response.message.toString()))
                        }
                        is com.rivaldo.core.data.remote.ApiResponse.Loading -> {
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