package com.example.flexexam.repository

import com.example.flexexam.data.MovieDao
import com.example.flexexam.enums.MovieType
import com.example.flexexam.model.Movie
import com.example.flexexam.model.MovieListResponse
import com.example.flexexam.network.MovieApi
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val movieDao: MovieDao
) {

    suspend fun getMovies(typeMovie: MovieType, page: Int): Response<MovieListResponse> {
        return if (typeMovie == MovieType.Popular) api.getPopularMovies(page = page)
        else api.getPlayingNowMovies(page = page)
    }

    suspend fun insertFavorite(movie: Movie) = movieDao.insertFavorite(movie)

    suspend fun getFavoriteMovie() = movieDao.getFavoriteMovie()

    suspend fun removeFavorite(idMovie: Int) = movieDao.removeFavorite(idMovie)

    suspend fun isFavorite(idMovie: Int) = movieDao.isFavorite(idMovie)
}