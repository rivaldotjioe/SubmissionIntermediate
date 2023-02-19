package com.rivaldo.core.data.remote.repository

import com.rivaldo.core.data.remote.api.RemoteDataSource
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StandardModel
import com.rivaldo.core.domain.repoInterface.IRegisterRepository
import com.rivaldo.core.utils.DataMapper.toStandardModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterRepository(val remoteDataSource: RemoteDataSource) :
    IRegisterRepository {
    override suspend fun register(
        name: String,
        password: String,
        email: String
    ): Flow<Resource<StandardModel>> {
        return flow {
            try {
                remoteDataSource.register(name, password, email).collect { resource ->
                    when (resource) {
                        is com.rivaldo.core.data.remote.ApiResponse.Success -> {
                            emit(Resource.Success(data = resource.data.toStandardModel()))
                        }
                        is com.rivaldo.core.data.remote.ApiResponse.Error -> {
                            emit(Resource.Error(message = resource.message.toString()))
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