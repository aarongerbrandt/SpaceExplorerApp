package com.example.finalproject.util

import java.text.SimpleDateFormat
import java.util.Locale

class DateFormats {
    companion object {
        val NASA_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val PRETTY_FORMAT = SimpleDateFormat("EEE, MMM d, yy", Locale.US)
        val PRETTY_FORMAT_NO_DAY_NAME = SimpleDateFormat("MMM d, yy", Locale.US)
        val SIMPLE_OUTPUT_FORMAT = SimpleDateFormat("M/d/yyyy")
    }
}