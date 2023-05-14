package com.example.finalproject.apod.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject.apod.Apod
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ApodDao {
    @Query("SELECT * FROM apod")
    fun getResponses(): Flow<List<Apod>>

    @Query("SELECT * FROM apod WHERE id=(:id)")
    suspend fun getResponseFromId(id: Long): Apod

    @Query("SELECT COUNT(*) FROM apod WHERE date=(:date)")
    suspend fun countResponsesByDate(date:String): Int

    @Insert
    suspend fun insertAll(responses: List<Apod>)

    @Insert
    suspend fun insert(apod: Apod)

    @Delete
    fun delete(response: Apod)
}