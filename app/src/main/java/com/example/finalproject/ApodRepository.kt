package com.example.finalproject

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.finalproject.apod.Apod
import com.example.finalproject.apod.database.ApodDatabase
import kotlinx.coroutines.flow.Flow
import java.util.*
private const val TAG = "ApodRepository"
private const val DATABASE_NAME = "APOD_DATABASE"

class ApodRepository private constructor(context: Context) {
    private val database: ApodDatabase = Room.databaseBuilder(
            context.applicationContext,
            ApodDatabase::class.java,
            DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()

    fun getApodResponses(): Flow<List<Apod>> = database.apodDao().getResponses()
    suspend fun addApodResponse(apod:Apod) = database.apodDao().insert(apod)
    suspend fun countResponseByDate(date:String) = database.apodDao().countResponsesByDate(date)
//    suspend fun getApodResponse(id: Long): Apod = database.apodDao().getResponse(id)
//    suspend fun addApodResponses(apods:List<Apod>) = database.apodDao().insertAll(apods)

    companion object {
        private var INSTANCE: ApodRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = ApodRepository(context)
                Log.d(TAG, "ApodRepo initialized!")
            }
        }

        fun get(): ApodRepository {
            Log.d(TAG, "Getting ApodRepo")
            return INSTANCE ?: throw IllegalStateException("ApodRepository must be initialized")
        }
    }
}