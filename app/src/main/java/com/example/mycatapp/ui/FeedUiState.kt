package com.example.mycatapp.ui

import com.example.mycatapp.data.CatImage

sealed interface FeedUiState {
    object Loading : FeedUiState
    data class Success(val catImages: List<CatImage>) : FeedUiState
    data class Error(val errorMessage: String? = null) : FeedUiState
}
