package com.example.flexexam.fragments.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexexam.model.Movie
import com.example.flexexam.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    fun insertFavorite(movie: Movie) = viewModelScope.launch {
        repository.insertFavorite(movie)
    }
}