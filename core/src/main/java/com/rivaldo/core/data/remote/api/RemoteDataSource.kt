package com.rivaldo.core.data.remote.api

import com.rivaldo.core.data.remote.model.ResponseGetAllStories
import com.rivaldo.core.data.remote.model.ResponseLogin
import com.rivaldo.core.data.remote.model.ResponseStandard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource(private val apiService: com.rivaldo.core.data.remote.api.ApiService) {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<com.rivaldo.core.data.remote.ApiResponse<ResponseStandard>> {
        return flow {
            emit(com.rivaldo.core.data.remote.ApiResponse.Loading(data = null))
            try {
                val responseRegister =
                    apiService.register(name = name, email = email, password = password)
                if (responseRegister.error == false) {
                    emit(com.rivaldo.core.data.remote.ApiResponse.Success(data = responseRegister))
                } else {
                    emit(
                        com.rivaldo.core.data.remote.ApiResponse.Error(
                            data = null,
                            message = responseRegister.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(com.rivaldo.core.data.remote.ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }

        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Flow<com.rivaldo.core.data.remote.ApiResponse<ResponseLogin>> {
        return flow {
            emit(com.rivaldo.core.data.remote.ApiResponse.Loading(data = null))
            try {
                val responseLogin = apiService.login(email = email, password = password)
                if (responseLogin.error == false) {
                    emit(com.rivaldo.core.data.remote.ApiResponse.Success(data = responseLogin))
                } else {
                    emit(com.rivaldo.core.data.remote.ApiResponse.Error(data = null, message = responseLogin.message.toString()))
                }

            } catch (e: Exception) {
                emit(com.rivaldo.core.data.remote.ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }

        }
    }

    suspend fun getAllStories(token: String): Flow<com.rivaldo.core.data.remote.ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories = apiService.getAllStories(token = "Bearer $token")
                if (responseGetAllStories.error == false) {
                    emit(com.rivaldo.core.data.remote.ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(
                        com.rivaldo.core.data.remote.ApiResponse.Error(
                            data = null,
                            message = responseGetAllStories.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(com.rivaldo.core.data.remote.ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun getAllStoriesWithLocation(token: String): Flow<com.rivaldo.core.data.remote.ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories =
                    apiService.getAllStoriesWithLocation(token = "Bearer $token")
                if (responseGetAllStories.error == false) {
                    emit(com.rivaldo.core.data.remote.ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(
                        com.rivaldo.core.data.remote.ApiResponse.Error(
                            data = null,
                            message = responseGetAllStories.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(com.rivaldo.core.data.remote.ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun getAllStoriesPaging(
        token: String,
        page: Int,
        size: Int
    ): Flow<com.rivaldo.core.data.remote.ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories =
                    apiService.getAllStories(token = "Bearer $token", page = page, size = size)
                if (responseGetAllStories.error == false) {
                    emit(com.rivaldo.core.data.remote.ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(
                        com.rivaldo.core.data.remote.ApiResponse.Error(
                            data = null,
                            message = responseGetAllStories.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(com.rivaldo.core.data.remote.ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun getAllStories(
        token: String,
        page: Int,
        location: Int
    ): Flow<com.rivaldo.core.data.remote.ApiResponse<ResponseGetAllStories>> {
        return flow {
            try {
                val responseGetAllStories = apiService.getAllStories(token = "Bearer $token")
                if (responseGetAllStories.error == false) {
                    emit(com.rivaldo.core.data.remote.ApiResponse.Success(data = responseGetAllStories))
                } else {
                    emit(
                        com.rivaldo.core.data.remote.ApiResponse.Error(
                            data = null,
                            message = responseGetAllStories.message.toString()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(com.rivaldo.core.data.remote.ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun addNewStories(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part
    ): Flow<com.rivaldo.core.data.remote.ApiResponse<ResponseStandard>> {
        return flow {
            try {
                val responseAddStory = apiService.addNewStories(
                    token = "Bearer $token",
                    description = description,
                    photo = image
                )
                if (responseAddStory.error == false) {
                    emit(com.rivaldo.core.data.remote.ApiResponse.Success(data = responseAddStory))
                } else {
                    emit(
                        com.rivaldo.core.data.remote.ApiResponse.Error(
                            data = null,
                            message = responseAddStory.message.toString()
                        )
                    )
                }

            } catch (e: Exception) {
                emit(com.rivaldo.core.data.remote.ApiResponse.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }
}