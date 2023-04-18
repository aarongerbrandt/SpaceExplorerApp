package com.example.finalproject.apod

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Apod(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") val id:Long,
    @ColumnInfo(name = "copyright") val copyright:String?,
    @ColumnInfo(name = "date") val date:String,
    @ColumnInfo(name = "explanation") val explanation:String?,
    @ColumnInfo(name = "hd_url") val hd_url: String?,
    @ColumnInfo(name = "media_type") val media_type: String?,
    @ColumnInfo(name = "service_version") val service_version:String?,
    @ColumnInfo(name = "title") val title:String?,
    @ColumnInfo(name = "url") val url:String
)
