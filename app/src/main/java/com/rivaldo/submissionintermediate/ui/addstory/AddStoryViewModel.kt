package com.rivaldo.submissionintermediate.ui.addstory

import androidx.lifecycle.ViewModel
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(val repository: IStoriesRepository, val preferences: DataStorePreferences) : ViewModel() {

    suspend fun getToken(): String {
        var token = ""
        token = preferences.getToken().first()
        return token
    }
    suspend fun addNewStory(description: RequestBody, image: MultipartBody.Part) = repository.addNewStory(getToken(), description, image)
}