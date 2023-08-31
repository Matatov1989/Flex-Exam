package com.example.flexexam.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flexexam.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}