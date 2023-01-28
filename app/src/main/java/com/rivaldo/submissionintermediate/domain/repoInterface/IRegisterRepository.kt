package com.rivaldo.submissionintermediate.domain.repoInterface

import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StandardModel
import kotlinx.coroutines.flow.Flow

interface IRegisterRepository {
    suspend fun register(name: String, password: String, email: String): Flow<Resource<StandardModel>>
}