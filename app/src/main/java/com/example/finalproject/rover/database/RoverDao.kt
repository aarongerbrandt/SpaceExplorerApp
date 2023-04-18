package com.example.finalproject.rover.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject.rover.Rover
import kotlinx.coroutines.flow.Flow

@Dao
interface RoverDao {
    @Query("SELECT * FROM rover")
    fun getResponses(): Flow<List<Rover>>

    @Query("SELECT * FROM rover WHERE image_id=(:id)")
    suspend fun getResponseFromId(id: Long): Rover

    @Query("SELECT COUNT(*) FROM rover WHERE image_id=(:id)")
    suspend fun countResponsesByImageId(id: Long): Int

    @Insert
    suspend fun insertAll(responses: List<Rover>)

    @Insert
    suspend fun insert(rover: Rover)

    @Delete
    fun delete(response: Rover)
}