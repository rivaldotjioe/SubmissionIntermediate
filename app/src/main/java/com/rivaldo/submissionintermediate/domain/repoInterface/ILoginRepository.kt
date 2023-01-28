package com.rivaldo.submissionintermediate.domain.repoInterface

import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.data.remote.model.ResponseLogin
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    suspend fun login(email: String, password: String): Flow<Resource<LoginModel>>
    suspend fun saveLoginData(loginModel: LoginModel)

}