package com.example.finalproject.apod.database

import androidx.room.*
import com.example.finalproject.DatabaseTypeConverters
import com.example.finalproject.apod.Apod

@Database(entities = [Apod::class], version = 2)
@TypeConverters(DatabaseTypeConverters::class)
abstract class ApodDatabase: RoomDatabase() {
    abstract fun apodDao(): ApodDao
}