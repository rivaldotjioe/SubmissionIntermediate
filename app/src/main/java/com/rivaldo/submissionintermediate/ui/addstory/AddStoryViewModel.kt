package com.rivaldo.submissionintermediate.ui.addstory

import androidx.lifecycle.ViewModel
import com.rivaldo.core.domain.useCase.AddStoryUseCase
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(val useCase: AddStoryUseCase) : ViewModel() {
    suspend fun getToken(): String {
        var token = ""
        token = useCase.getToken().first()
        return token
    }
    suspend fun addNewStory(description: RequestBody, image: MultipartBody.Part) = useCase.addNewStory(getToken(), description, image)
}