package com.example.finalproject

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DatabaseTypeConverters {
    private val nasaFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    @TypeConverter
    fun fromDate(date: Date):String {
        return nasaFormat.format(date)
    }

    @TypeConverter
    fun toDate(date: String): Date {
        return nasaFormat.parse(date)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun toUUID(str:String): UUID {
        return UUID.fromString(str)
    }
}