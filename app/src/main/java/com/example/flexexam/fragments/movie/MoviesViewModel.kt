package com.example.flexexam.fragments.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.flexexam.data.MovieUiState
import com.example.flexexam.enums.MovieType
import com.example.flexexam.repository.MovieRepository
import com.example.flexexam.source.MoviePagingSource
import com.example.flexexam.util.Constants.SIZE_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

//    private val movieUiState = MutableStateFlow<MovieUiState>(MovieUiState.Success(emptyList()))
//    val movieLiveData: StateFlow<MovieUiState> = movieUiState
//
//    fun getMovies(typeMovie: MovieType) {
//        viewModelScope.launch {
//            try {
//                movieUiState.value = MovieUiState.Loading(true)
//                if (typeMovie == MovieType.Favorite) {
//                    movieUiState.value = MovieUiState.Success(repository.getFavoriteMovie())
//                } else {
//                    val response = repository.getMovies(typeMovie)
//                    val list = response.body()?.results
//                    list?.let {
//                        movieUiState.value = MovieUiState.Success(it)
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("RESULT_EXCEPTION", "result: $e")
//                movieUiState.value = MovieUiState.Error(e)
//            } finally {
//                movieUiState.value = MovieUiState.Loading(false)
//            }
//        }
//    }



    private val apiKey = "YOUR_API_KEY_HERE"

    private val movieUiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading(false))
    val movieLiveData: StateFlow<MovieUiState> = movieUiState



    fun fetchMovies(typeMovie: MovieType) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pagingData = Pager(
                    PagingConfig(pageSize = SIZE_PAGE, initialLoadSize  = SIZE_PAGE)) {
                    MoviePagingSource(repository, typeMovie)
                }.flow.cachedIn(viewModelScope)

                pagingData.collectLatest { data ->
                    movieUiState.value = MovieUiState.Success(data)
                }

            } catch (e: Exception) {
                Log.e("RESULT_EXCEPTION", "result: $e")
                movieUiState.value = MovieUiState.Error(e)
            }  finally {
                movieUiState.value = MovieUiState.Loading(false)
            }
        }
    }
}