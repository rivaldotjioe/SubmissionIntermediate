package com.rivaldo.submissionintermediate.domain.repoInterface

import com.rivaldo.submissionintermediate.data.remote.Resource
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import com.rivaldo.submissionintermediate.domain.model.StandardModel
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {
    suspend fun login(username: String, password: String): Flow<Resource<LoginModel>>

}