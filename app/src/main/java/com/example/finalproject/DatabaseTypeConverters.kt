package com.example.finalproject

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DatabaseTypeConverters {
    private val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.US)

    @TypeConverter
    fun fromDate(date: Date):String {
        return dateFormatter.format(date)
    }

    @TypeConverter
    fun toDate(date: String): Date {
        return dateFormatter.parse(date)!!
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