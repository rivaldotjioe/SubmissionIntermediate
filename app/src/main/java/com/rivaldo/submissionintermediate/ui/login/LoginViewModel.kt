package com.rivaldo.submissionintermediate.ui.login

import androidx.lifecycle.ViewModel
import com.rivaldo.core.domain.model.LoginModel
import com.rivaldo.core.domain.useCase.LoginUseCase

class LoginViewModel(val useCase: LoginUseCase) : ViewModel() {
    suspend fun login(email: String, password: String) = useCase.login(email, password)
    suspend fun saveLoginData(loginModel: LoginModel) {
        useCase.saveLoginData(loginModel)
    }
}