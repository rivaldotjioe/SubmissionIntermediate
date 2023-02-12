package com.rivaldo.submissionintermediate.ui.maps

import androidx.lifecycle.ViewModel
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import kotlinx.coroutines.flow.first

class ListStoryMapsViewModel(val repository: IStoriesRepository, val preferences: DataStorePreferences) : ViewModel() {

    suspend fun getToken(): String {
        var token = ""
        token = preferences.getToken().first()
        return token
    }

    suspend fun getListStory() = repository.getAllStoriesWithLocation(getToken())
}