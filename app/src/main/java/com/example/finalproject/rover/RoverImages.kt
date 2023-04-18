package com.example.finalproject.rover

import java.util.Date

data class RoverImages(
    val photos: List<ImageDetails>
)

data class ImageDetails (
    val id: Long,
    val sol: Long,
    val camera: CameraDetails,
    val img_src: String,
    val earth_date: Date,
    val rover: RoverDetails
)

data class CameraDetails (
    val id: Long,
    val name: String,
    val rover_id: Long,
    val full_name: String
)

data class RoverDetails (
    val id: Long,
    val name: String,
    val landing_date: Date,
    val launch_date: Date,
    val status: String
)