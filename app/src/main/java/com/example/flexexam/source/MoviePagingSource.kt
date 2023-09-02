package com.example.flexexam.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flexexam.enums.MovieType
import com.example.flexexam.model.Movie
import com.example.flexexam.repository.MovieRepository
import com.example.flexexam.util.Constants.SIZE_PAGE

class MoviePagingSource (
    private val repository: MovieRepository,
    private val typeMovie: MovieType
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1

            if (typeMovie == MovieType.Favorite) {
                loadFromLocalStorage(page)
            } else {
                loadFromApi(page)
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

    private suspend fun loadFromLocalStorage(page: Int): LoadResult<Int, Movie> {
        val movies = repository.getFavoriteMovie()
        return if (movies.isNotEmpty()) {
            var nextPage = if (movies.isNotEmpty()) page + 1 else null
            if (movies.size <= SIZE_PAGE) {
                nextPage = null
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )
        } else {
            LoadResult.Error(Exception("Failed: data is empty"))
        }
    }

    private suspend fun loadFromApi(page: Int): LoadResult<Int, Movie> {
        val response = repository.getMovies(typeMovie, page)

        return if (response.isSuccessful) {
            val movies = response.body()?.results ?: emptyList()
            val nextPage = if (movies.isNotEmpty()) page + 1 else null

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )
        } else {
            LoadResult.Error(Exception("Failed to load data"))
        }
    }
}