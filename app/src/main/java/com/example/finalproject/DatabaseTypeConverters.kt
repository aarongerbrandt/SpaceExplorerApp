package com.example.finalproject

import androidx.room.TypeConverter
import com.example.finalproject.util.DateFormats
import java.util.Date
import java.util.UUID

class DatabaseTypeConverters {
    @TypeConverter
    fun fromDate(date: Date):String {
        return DateFormats.NASA_FORMAT.format(date)
    }

    @TypeConverter
    fun toDate(date: String): Date {
        return DateFormats.NASA_FORMAT.parse(date)
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