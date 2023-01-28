package com.rivaldo.submissionintermediate.data.remote.api

import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.data.remote.model.ResponseGetAllStories
import com.rivaldo.submissionintermediate.data.remote.model.ResponseLogin
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import com.rivaldo.submissionintermediate.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<ResponseStandard>> {
        return flow {
            emit(ApiResponse.Loading(data = null))
            try {
                val responseRegister =
                    apiService.register(name = name, email = email, password = password)
                if (responseRegister.error == false) {
                    emit(ApiResponse.Success(data = responseRegister))
                } else {
                    emit(ApiResponse.Error(data = null, message = responseRegister.message.toString()))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }

        }
    }
    suspend fun login(
        email: String,
        password: String
        ) : Flow<ApiResponse<ResponseLogin>> {
        return flow {
            try {
                val responseLogin = apiService.login(email = email, password = password)
                if (responseLogin.error == false) {
                    emit(ApiResponse.Success(data = responseLogin))
                } else {
                    emit(ApiResponse.Error(data = null, message = responseLogin.message.toString()))
                }

            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }

        }
    }

    suspend fun getAllStories(token: String) : Flow<ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories = apiService.getAllStories(token = "Bearer $token")
                if (responseGetAllStories.error == false) {
                    emit(ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(ApiResponse.Error(data = null, message = responseGetAllStories.message.toString()))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }
}