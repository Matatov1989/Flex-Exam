package com.example.flexexam.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flexexam.enums.MovieType
import com.example.flexexam.model.Movie
import com.example.flexexam.repository.MovieRepository

class MoviePagingSource (
    private val repository: MovieRepository,
    private val typeMovie: MovieType
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1

            if (typeMovie == MovieType.Favorite) {
                val response = repository.getFavoriteMovie()

                LoadResult.Page(
                    data = response,
                    prevKey = null,
                    nextKey = null
                )
            } else {
                val response = repository.getMovies(typeMovie, page)

                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    val nextPage = if (movies.isNotEmpty()) page + 1 else null

                    LoadResult.Page(
                        data = movies,
                        prevKey = null,
                        nextKey = nextPage
                    )
                } else {
                    LoadResult.Error(Exception("Failed to load data"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}