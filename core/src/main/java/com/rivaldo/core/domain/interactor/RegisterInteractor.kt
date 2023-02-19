package com.rivaldo.core.domain.interactor

import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StandardModel
import com.rivaldo.core.domain.repoInterface.IRegisterRepository
import com.rivaldo.core.domain.useCase.RegisterUseCase
import kotlinx.coroutines.flow.Flow

class RegisterInteractor(val repository: IRegisterRepository) : RegisterUseCase {
    override suspend fun register(
        name: String,
        password: String,
        email: String
    ): Flow<Resource<StandardModel>> = repository.register(name, password, email)
}