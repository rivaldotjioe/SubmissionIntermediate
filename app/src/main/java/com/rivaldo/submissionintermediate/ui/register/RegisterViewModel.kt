package com.rivaldo.submissionintermediate.ui.register

import androidx.lifecycle.ViewModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IRegisterRepository
import kotlinx.coroutines.flow.Flow

class RegisterViewModel(val repository: IRegisterRepository) : ViewModel() {
    suspend fun register(name: String, password: String, email: String) = repository.register(name, password, email)
}