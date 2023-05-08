package com.example.finalproject

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class DatabaseTypeConverters {
    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun fromDate(date: Date):String {
        // Create individual date formats bc SimpleDateFormat is not thread safe
        val nasaFormat = SimpleDateFormat("yyyy-MM-dd")
        return nasaFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun toDate(dateStr: String): Date {
        // Create individual date formats bc SimpleDateFormat is not thread safe
        val nasaFormat = SimpleDateFormat("yyyy-MM-dd")
        return nasaFormat.parse(dateStr)!!
    }
}