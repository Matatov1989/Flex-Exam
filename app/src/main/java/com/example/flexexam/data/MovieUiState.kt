package com.example.flexexam.data

import androidx.paging.PagingData
import com.example.flexexam.model.Movie
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

sealed class MovieUiState {
    data class Success(val movies: PagingData<Movie>) : MovieUiState()
    data class Error(val exception: Exception) : MovieUiState()
    data class Loading(val isLoad: Boolean) : MovieUiState()
}
