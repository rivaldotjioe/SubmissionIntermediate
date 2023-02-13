package com.rivaldo.submissionintermediate.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(
    val storiesRepository: IStoriesRepository,
    val preferences: DataStorePreferences
) : ViewModel() {

    val storiesFlow : Flow<PagingData<StoryModel>> by lazy { storiesRepository.getAllStoriesPaging().cachedIn(viewModelScope) }
    fun checkIsLogin() = preferences.getIsLoggedIn()

    suspend fun getToken(): String {
        var token = ""
//        runBlocking {
            token = preferences.getToken().first()
//        }

        return token
    }

   suspend fun getListStory() = storiesRepository.getAllStories(getToken())

    fun logout() {
        viewModelScope.launch {
            preferences.clearDataLogout()
        }

    }
}