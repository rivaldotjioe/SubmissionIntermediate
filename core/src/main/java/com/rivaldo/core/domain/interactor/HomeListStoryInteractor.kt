package com.rivaldo.core.domain.interactor

import androidx.paging.PagingData
import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.core.domain.repoInterface.IStoriesRepository
import com.rivaldo.core.domain.useCase.HomeListStoryUseCase
import kotlinx.coroutines.flow.Flow

class HomeListStoryInteractor(val repository : IStoriesRepository, val preferences: DataStorePreferences) :
    HomeListStoryUseCase {
    override fun getIsLoggedIn(): Flow<Boolean>  = preferences.getIsLoggedIn()

    override fun getToken(): Flow<String> = preferences.getToken()

    override fun getAllStoriesPaging(): Flow<PagingData<StoryModel>> = repository.getAllStoriesPaging()

    override suspend fun logout() = preferences.clearDataLogout()
}