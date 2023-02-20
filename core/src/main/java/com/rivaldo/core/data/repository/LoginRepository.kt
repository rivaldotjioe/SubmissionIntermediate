package com.rivaldo.core.data.repository

import com.rivaldo.core.data.remote.api.RemoteDataSource
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.LoginModel
import com.rivaldo.core.domain.repoInterface.ILoginRepository
import com.rivaldo.core.utils.DataMapper.toLoginModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository(val remoteDataSource: RemoteDataSource) :
    ILoginRepository {
    override suspend fun login(email: String, password: String): Flow<Resource<LoginModel>> {
        return flow {
            try {
                remoteDataSource.login(email = email, password = password).collect { resource ->
                    when (resource) {
                        is com.rivaldo.core.data.remote.ApiResponse.Success -> {
                            resource.data?.loginResult.let {
                                if (it != null) {
                                    emit(Resource.Success(it.toLoginModel()))
                                }
                            }
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

    override suspend fun saveLoginData(loginModel: LoginModel) {
        TODO("Not yet implemented")
    }

}