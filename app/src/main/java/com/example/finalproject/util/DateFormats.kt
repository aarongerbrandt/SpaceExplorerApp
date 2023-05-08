package com.example.finalproject.util

import java.text.SimpleDateFormat

class DateFormats {
    companion object {
        val NASA_FORMAT = SimpleDateFormat("yyyy-MM-dd")
        val PRETTY_FORMAT = SimpleDateFormat("EEE, MMM d, yy")
        val PRETTY_FORMAT_NO_DAY_NAME = SimpleDateFormat("MMM d, yy")
        val SIMPLE_OUTPUT_FORMAT = SimpleDateFormat("M/d/yyyy")
    }
}