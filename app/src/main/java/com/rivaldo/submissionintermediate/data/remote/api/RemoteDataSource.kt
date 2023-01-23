package com.rivaldo.submissionintermediate.data.remote.api

import com.rivaldo.submissionintermediate.data.remote.Resource
import com.rivaldo.submissionintermediate.data.remote.model.ResponseStandard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<ResponseStandard>> {
        return flow {
            emit(Resource.Loading(data = null))
            try {
                val responseRegister =
                    apiService.register(name = name, email = email, password = password)
                if (responseRegister.error == false) {
                    emit(Resource.Success(data = responseRegister))
                } else {
                    emit(Resource.Error(data = null, message = responseRegister.message.toString()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(data = null, message = e.message ?: "Error Occurred!"))
            }

        }
    }
}