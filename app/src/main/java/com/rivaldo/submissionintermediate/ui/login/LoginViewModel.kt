package com.rivaldo.submissionintermediate.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import com.rivaldo.submissionintermediate.domain.repoInterface.ILoginRepository
import com.rivaldo.submissionintermediate.domain.useCase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(val useCase: LoginUseCase) : ViewModel() {
    suspend fun login(email: String, password: String) = useCase.login(email, password)
    suspend fun saveLoginData(loginModel: LoginModel) {
        useCase.saveLoginData(loginModel)
    }
}