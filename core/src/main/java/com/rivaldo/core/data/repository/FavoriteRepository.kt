package com.rivaldo.core.data.repository

import com.rivaldo.core.data.local.LocalDataSource
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.core.domain.repoInterface.IFavoriteRepository
import com.rivaldo.core.utils.DataMapper.toStoryEntity
import com.rivaldo.core.utils.DataMapper.toStoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepository(val localDataSource: LocalDataSource) : IFavoriteRepository {
    override fun getAllStoriesFavorite(): Flow<Resource<List<StoryModel>>> {
        return flow {
            emit(Resource.Loading(data = null))
            try {
                localDataSource.getAllStoriesFavorite().collect { list ->
                    if (list.isNotEmpty()) {
                        emit(Resource.Success(data = list.toStoryModel() ))
                    } else {
                        emit(Resource.Success(data = emptyList()))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

    override suspend fun insertFavorite(story: StoryModel): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading(data = null))
            try {
                localDataSource.insertFavorite(story.toStoryEntity())
                emit(Resource.Success(data = "Success"))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

    override suspend fun deleteFavorite(story: StoryModel): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading(data = null))
            try {
                localDataSource.deleteFavorite(story.toStoryEntity())
                emit(Resource.Success(data = "Success"))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }

    override fun isRowExist(id: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(data = null))
            try {
                emit(Resource.Success(data = localDataSource.isRowExist(id)))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }
    }
}