package com.example.flexexam.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flexexam.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: Movie)

    @Query("SELECT * FROM MovieTable")
    suspend fun getFavoriteMovie(): List<Movie>
}