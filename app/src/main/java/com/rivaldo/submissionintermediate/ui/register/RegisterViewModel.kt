package com.rivaldo.submissionintermediate.ui.register

import androidx.lifecycle.ViewModel
import com.rivaldo.core.domain.useCase.RegisterUseCase

class RegisterViewModel(val useCase: RegisterUseCase) : ViewModel() {
    suspend fun register(name: String, password: String, email: String) = useCase.register(name, password, email)
}