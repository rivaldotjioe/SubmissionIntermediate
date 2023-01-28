package com.rivaldo.submissionintermediate.ui.main

import androidx.lifecycle.ViewModel
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainViewModel(
    val storiesRepository: IStoriesRepository,
    val preferences: DataStorePreferences
) : ViewModel() {
    fun checkIsLogin() = preferences.getIsLoggedIn()

    suspend fun getToken(): String {
        var token = ""
//        runBlocking {
            token = preferences.getToken().first()
//        }
        return token
    }

   suspend fun getListStory() = storiesRepository.getAllStories(getToken())
}