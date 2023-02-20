package com.rivaldo.submissionintermediate.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.core.domain.useCase.DetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(val useCase: DetailUseCase) : ViewModel() {
    private val _isFavorite : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite : StateFlow<Boolean> = _isFavorite

    fun getIsFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.isFavorite(id).collect {
                _isFavorite.emit(it.data ?: false)
            }
        }
    }

    suspend fun addToFavorite(story: StoryModel) {
        _isFavorite.emit(_isFavorite.value.not())
        useCase.addToFavorite(story).collect{
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    println(it.data)
                }
                is Resource.Error -> {
                    println(it.message)
                }
            }
        }
    }

    suspend fun deleteFavorite(story: StoryModel) {
        _isFavorite.emit(_isFavorite.value.not())
        useCase.deleteFavorite(story).collect()
    }
}