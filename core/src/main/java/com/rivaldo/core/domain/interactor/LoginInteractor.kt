package com.rivaldo.core.domain.interactor

import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.core.domain.model.LoginModel
import com.rivaldo.core.domain.repoInterface.ILoginRepository
import com.rivaldo.core.domain.useCase.LoginUseCase


class LoginInteractor(val repository: ILoginRepository, val preferences: DataStorePreferences) :
    LoginUseCase {
    override suspend fun login(email: String, password: String) = repository.login(email, password)
    override suspend fun saveLoginData(loginModel: LoginModel) {
            preferences.setUserID(loginModel.userId)
            preferences.setToken(loginModel.token)
            preferences.setUserName(loginModel.name)
            preferences.setIsLoggedIn(true)
    }
}