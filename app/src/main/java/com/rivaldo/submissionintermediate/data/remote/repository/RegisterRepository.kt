package com.rivaldo.submissionintermediate.data.remote.repository

import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.data.remote.api.RemoteDataSource
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StandardModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IRegisterRepository
import com.rivaldo.submissionintermediate.utils.DataMapper.toStandardModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterRepository(val remoteDataSource: RemoteDataSource) : IRegisterRepository {
    override suspend fun register(
        name: String,
        password: String,
        email: String
    ): Flow<Resource<StandardModel>> {
        return flow {
            try {
                remoteDataSource.register(name, password, email).collect { resource ->
                    when (resource) {
                        is ApiResponse.Success -> {
                            emit(Resource.Success(data = resource.data.toStandardModel()))
                        }
                        is ApiResponse.Error -> {
                            emit(Resource.Error(message = resource.message.toString()))
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