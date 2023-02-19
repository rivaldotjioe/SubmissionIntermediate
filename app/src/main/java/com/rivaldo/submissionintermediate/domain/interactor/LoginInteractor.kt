package com.rivaldo.submissionintermediate.domain.interactor

import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import com.rivaldo.submissionintermediate.domain.repoInterface.ILoginRepository
import com.rivaldo.submissionintermediate.domain.useCase.LoginUseCase


class LoginInteractor(val repository: ILoginRepository, val preferences: DataStorePreferences) : LoginUseCase {
    override suspend fun login(email: String, password: String) = repository.login(email, password)
    override suspend fun saveLoginData(loginModel: LoginModel) {
            preferences.setUserID(loginModel.userId)
            preferences.setToken(loginModel.token)
            preferences.setUserName(loginModel.name)
            preferences.setIsLoggedIn(true)
    }
}