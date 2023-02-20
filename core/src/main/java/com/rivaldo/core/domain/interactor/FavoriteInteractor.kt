package com.rivaldo.core.domain.interactor

import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.core.domain.repoInterface.IFavoriteRepository
import com.rivaldo.core.domain.useCase.FavoriteUseCase
import kotlinx.coroutines.flow.Flow

class FavoriteInteractor (val repository: IFavoriteRepository, val preferences: DataStorePreferences): FavoriteUseCase {
    override fun getAllStoriesFavorite(): Flow<Resource<List<StoryModel>>> = repository.getAllStoriesFavorite()

    override suspend fun insertFavorite(stories: StoryModel): Flow<Resource<String>> = repository.insertFavorite(stories)

    override suspend fun deleteFavorite(stories: StoryModel): Flow<Resource<String>> = repository.deleteFavorite(stories)

    override fun isRowExist(id: String): Flow<Resource<Boolean>> = repository.isRowExist(id)
}