package com.example.mycatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycatapp.data.CatImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import com.example.mycatapp.data.repository.CatRepository
import kotlinx.coroutines.flow.update

class FeedViewModel(
    private val repository: CatRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var currentPage = 0
    private var hasMoreData = true
    private val _isFetching = MutableStateFlow(false)
    val isFetching = _isFetching.asStateFlow()

    init {
        viewModelScope.launch {
            fetchMoreImages()
        }
    }

    fun fetchMoreImages() {
        if (_isFetching.value || !hasMoreData) return
        _isFetching.value = true

        viewModelScope.launch {
            try {
                // Call the repository to fetch the data
                // Note: Make sure CatRepository.getRandomCats() is updated to accept the 'page' parameter!
                val cats = repository.getRandomCats(currentPage).map { image ->
                    CatImage(image.url)
                }
                
                // If the API returns nothing, we've hit the end of the database!
                if (cats.isEmpty()) {
                    hasMoreData = false
                }
                
                // Append the new cats to the existing list!
                val currentCats = (_uiState.value as? FeedUiState.Success)?.catImages ?: emptyList()
                _uiState.update { FeedUiState.Success(currentCats + cats) }

                currentPage++
            } catch (e: Exception) {
                e.printStackTrace()
                // Only show error if we have no cats yet, otherwise we might wipe the feed
                if (_uiState.value !is FeedUiState.Success) {
                    _uiState.value = FeedUiState.Error(e.message)
                }
            } finally {
                _isFetching.value = false
            }
        }
    }
}