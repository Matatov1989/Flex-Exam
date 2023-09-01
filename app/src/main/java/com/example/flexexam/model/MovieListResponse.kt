package com.example.flexexam.model

data class MovieListResponse(
    val results: List<Movie>,
    val page: Int,
    val total_pages: Int
)
