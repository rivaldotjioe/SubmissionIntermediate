package com.rivaldo.submissionintermediate.data.remote.api

import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.data.remote.model.ResponseGetAllStories
import com.rivaldo.submissionintermediate.data.remote.model.ResponseLogin
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

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
                    emit(
                        ApiResponse.Error(
                            data = null,
                            message = responseRegister.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }

        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Flow<ApiResponse<ResponseLogin>> {
        return flow {
            emit(ApiResponse.Loading(data = null))
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

    suspend fun getAllStories(token: String): Flow<ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories = apiService.getAllStories(token = "Bearer $token")
                if (responseGetAllStories.error == false) {
                    emit(ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(
                        ApiResponse.Error(
                            data = null,
                            message = responseGetAllStories.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun getAllStoriesWithLocation(token: String): Flow<ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories =
                    apiService.getAllStoriesWithLocation(token = "Bearer $token")
                if (responseGetAllStories.error == false) {
                    emit(ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(
                        ApiResponse.Error(
                            data = null,
                            message = responseGetAllStories.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun getAllStoriesPaging(
        token: String,
        page: Int,
        size: Int
    ): Flow<ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories =
                    apiService.getAllStories(token = "Bearer $token", page = page, size = size)
                if (responseGetAllStories.error == false) {
                    emit(ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(
                        ApiResponse.Error(
                            data = null,
                            message = responseGetAllStories.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun getAllStories(
        token: String,
        page: Int,
        location: Int
    ): Flow<ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories = apiService.getAllStories(token = "Bearer $token")
                if (responseGetAllStories.error == false) {
                    emit(ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(
                        ApiResponse.Error(
                            data = null,
                            message = responseGetAllStories.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun addNewStories(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part
    ): Flow<ApiResponse<ResponseStandard>> {
        return flow {
            try {
                val responseAddStory = apiService.addNewStories(
                    token = "Bearer $token",
                    description = description,
                    photo = image
                )
                if (responseAddStory.error == false) {
                    emit(ApiResponse.Success(data = responseAddStory))
                } else {
                    emit(
                        ApiResponse.Error(
                            data = null,
                            message = responseAddStory.message.toString()
                        )
                    )
                }

            } catch (e: Exception) {
                emit(ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }
}