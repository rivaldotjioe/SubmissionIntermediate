package com.rivaldo.submissionintermediate.domain.interactor

import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.StandardModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IRegisterRepository
import com.rivaldo.submissionintermediate.domain.useCase.RegisterUseCase
import kotlinx.coroutines.flow.Flow

class RegisterInteractor(val repository: IRegisterRepository) : RegisterUseCase {
    override suspend fun register(
        name: String,
        password: String,
        email: String
    ): Flow<Resource<StandardModel>> = repository.register(name, password, email)
}