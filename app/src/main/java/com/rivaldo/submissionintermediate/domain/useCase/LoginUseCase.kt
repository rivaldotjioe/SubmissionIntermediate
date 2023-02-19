package com.rivaldo.submissionintermediate.domain.useCase

import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    suspend fun login(email: String, password: String) : Flow<Resource<LoginModel>>

    suspend fun saveLoginData(loginModel: LoginModel)
}