package com.rivaldo.submissionintermediate.data.remote.repository

import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.data.remote.api.RemoteDataSource
import com.rivaldo.submissionintermediate.data.remote.model.LoginResult
import com.rivaldo.submissionintermediate.data.remote.model.ResponseLogin
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import com.rivaldo.submissionintermediate.domain.repoInterface.ILoginRepository
import com.rivaldo.submissionintermediate.utils.DataMapper.toLoginModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoginRepository(val remoteDataSource: RemoteDataSource) : ILoginRepository {
    override suspend fun login(email: String, password: String): Flow<Resource<LoginModel>> {
        return flow {
            try {
                remoteDataSource.login(email = email, password = password).collect { resource ->
                    when (resource) {
                        is ApiResponse.Success -> {
                            resource.data?.loginResult.let {
                                if (it != null) {
                                    emit(Resource.Success(it.toLoginModel()))
                                }
                            }
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

    override suspend fun saveLoginData(loginModel: LoginModel) {
        TODO("Not yet implemented")
    }

}