package com.rivaldo.submissionintermediate.ui.maps

import androidx.lifecycle.ViewModel
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.domain.useCase.StoryMapsUseCase
import kotlinx.coroutines.flow.first

class ListStoryMapsViewModel(val useCase: StoryMapsUseCase) : ViewModel() {

    suspend fun getToken(): String {
        var token = ""
        token = useCase.getToken().first()
        return token
    }

    suspend fun getListStory() = useCase.getListStory(getToken())
}