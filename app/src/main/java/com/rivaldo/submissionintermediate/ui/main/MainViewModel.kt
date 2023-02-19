package com.rivaldo.submissionintermediate.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.domain.useCase.HomeListStoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(
    val useCase: HomeListStoryUseCase
) : ViewModel() {

    val storiesFlow : Flow<PagingData<StoryModel>> by lazy { useCase.getAllStoriesPaging().cachedIn(viewModelScope) }
    fun checkIsLogin() = useCase.getIsLoggedIn()

    suspend fun getToken(): String {
        var token = ""
//        runBlocking {
            token = useCase.getToken().first()
//        }

        return token
    }


    fun logout() {
        viewModelScope.launch {
            useCase.logout()
        }

    }
}