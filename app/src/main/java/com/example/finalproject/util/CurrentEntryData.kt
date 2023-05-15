package com.example.finalproject.util

import com.example.finalproject.apod.Apod
import com.example.finalproject.rover.Rover

class CurrentEntryData {
    companion object {
        var currentApod: Apod? = null
        var currentRover: Rover? = null
    }
}