package com.example.finalproject

import androidx.room.TypeConverter
import com.example.finalproject.util.DateFormats
import java.util.Date

class DatabaseTypeConverters {
    @TypeConverter
    fun fromDate(date: Date):String {
        return DateFormats.NASA_FORMAT.format(date)
    }

    @TypeConverter
    fun toDate(dateStr: String): Date {
        return DateFormats.NASA_FORMAT.parse(dateStr)!!
    }
}