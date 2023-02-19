package com.rivaldo.core.domain.repoInterface

import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StandardModel
import kotlinx.coroutines.flow.Flow

interface IRegisterRepository {
    suspend fun register(name: String, password: String, email: String): Flow<Resource<StandardModel>>
}