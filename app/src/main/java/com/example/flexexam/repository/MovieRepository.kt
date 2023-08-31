package com.example.flexexam.repository

import com.example.flexexam.enums.MovieType
import com.example.flexexam.model.MovieListResponse
import com.example.flexexam.network.MovieApi
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi
) {

    suspend fun getMovies(typeMovie: MovieType): Response<MovieListResponse> {
        return if (typeMovie == MovieType.Popular) api.getPopularMovies()
        else api.getPlayingNowMovies()
    }


}