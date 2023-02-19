package com.rivaldo.core.domain.useCase

import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StandardModel
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    suspend fun register(name: String, password: String, email: String) : Flow<Resource<StandardModel>>
}