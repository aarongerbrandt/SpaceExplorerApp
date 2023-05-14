package com.example.finalproject.rover

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Rover(
    @PrimaryKey(autoGenerate = true) val response_id: Long,
    val image_id: Long = 0,
    val sol: Long,
    val img_src: String,
    val earth_date: Date,

    val camera_id: Long,
    val camera_name: String,
    val camera_full_name: String,

    val rover_id: Long,
    val rover_name: String,
    val rover_landing_date: Date,
    val rover_launch_date: Date,
    val rover_status: String
)