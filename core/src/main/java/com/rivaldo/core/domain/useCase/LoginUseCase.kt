package com.rivaldo.core.domain.useCase

import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.LoginModel
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    suspend fun login(email: String, password: String) : Flow<Resource<LoginModel>>

    suspend fun saveLoginData(loginModel: LoginModel)
}