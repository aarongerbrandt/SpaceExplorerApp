package com.example.finalproject

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.finalproject.rover.Rover
import com.example.finalproject.rover.database.RoverDatabase
import kotlinx.coroutines.flow.Flow

private const val TAG = "RoverRepository"
private const val DATABASE_NAME = "ROVER_DATABASE"

class RoverRepository private constructor(context: Context){
    private val database: RoverDatabase = Room.databaseBuilder(
            context.applicationContext,
            RoverDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
        .build()

    fun getRoverResponses(): Flow<List<Rover>> {
        return database.roverDao().getResponses()
    }
    suspend fun addRoverResponse(rover: Rover) = database.roverDao().insert(rover)
    suspend fun countResponseByImageId(id:Long) = database.roverDao().countResponsesByImageId(id)

    companion object {
        private var INSTANCE: RoverRepository? = null

        fun initialize(context: Context) {
            Log.d(TAG, "Initializing RoverRepo!")
            if(INSTANCE == null) {
                INSTANCE = RoverRepository(context)
            }
        }

        fun get(): RoverRepository {
            Log.d(TAG, "Getting RoverRepo")
            return INSTANCE ?: throw java.lang.IllegalStateException("RoverRepository must be initialized")
        }
    }
}