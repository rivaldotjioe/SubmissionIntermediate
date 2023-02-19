package com.rivaldo.submissionintermediate.domain.useCase

import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StandardModel
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    suspend fun register(name: String, password: String, email: String) : Flow<Resource<StandardModel>>
}