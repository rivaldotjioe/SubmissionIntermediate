package com.rivaldo.core.domain.interactor

import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.core.domain.repoInterface.IFavoriteRepository
import com.rivaldo.core.domain.useCase.DetailUseCase
import kotlinx.coroutines.flow.Flow

class DetailInteractor(val favoriteRepository: IFavoriteRepository) : DetailUseCase {
    override suspend fun isFavorite(id: String): Flow<Resource<Boolean>> = favoriteRepository.isRowExist(id)

    override suspend fun addToFavorite(story: StoryModel): Flow<Resource<String>> = favoriteRepository.insertFavorite(story)

    override suspend fun deleteFavorite(story: StoryModel): Flow<Resource<String>> = favoriteRepository.deleteFavorite(story)
}