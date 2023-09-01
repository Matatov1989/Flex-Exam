package com.example.flexexam.fragments.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexexam.model.Movie
import com.example.flexexam.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val isFavoriteLiveData = MutableLiveData<Boolean>()

    fun insertFavorite(movie: Movie) = viewModelScope.launch {
        repository.insertFavorite(movie)
    }

    fun removeFavorite(idMovie: Int) = viewModelScope.launch {
        repository.removeFavorite(idMovie)
    }

    fun isFavorite(idMovie: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isFavorite(idMovie)
            if (result == 0)
                isFavoriteLiveData.postValue(false)
            else if(result > 0)
                isFavoriteLiveData.postValue(true)
        }
    }
}