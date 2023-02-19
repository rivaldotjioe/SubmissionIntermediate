package com.rivaldo.submissionintermediate.ui.register

import androidx.lifecycle.ViewModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IRegisterRepository
import com.rivaldo.submissionintermediate.domain.useCase.RegisterUseCase
import kotlinx.coroutines.flow.Flow

class RegisterViewModel(val useCase: RegisterUseCase) : ViewModel() {
    suspend fun register(name: String, password: String, email: String) = useCase.register(name, password, email)
}