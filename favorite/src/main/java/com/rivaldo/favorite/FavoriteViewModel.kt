package com.rivaldo.favorite

import androidx.lifecycle.ViewModel
import com.rivaldo.core.domain.useCase.FavoriteUseCase

class FavoriteViewModel(val useCase: FavoriteUseCase) : ViewModel(){

    fun getAllStoriesFavorite() = useCase.getAllStoriesFavorite()
}