package com.example.finalproject.rover.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.finalproject.DatabaseTypeConverters
import com.example.finalproject.rover.Rover

@Database(entities = [Rover::class], version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class RoverDatabase: RoomDatabase() {
    abstract fun roverDao(): RoverDao
}