package com.rivaldo.submissionintermediate.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import com.rivaldo.submissionintermediate.domain.repoInterface.ILoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(val repository: ILoginRepository, val preferences: DataStorePreferences) :
    ViewModel() {
    suspend fun login(email: String, password: String) = repository.login(email, password)
    fun saveLoginData(loginModel: LoginModel) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                preferences.setUserID(loginModel.userId)
                preferences.setToken(loginModel.token)
                preferences.setUserName(loginModel.name)
                preferences.setIsLoggedIn(true)
            }
        }
    }
}