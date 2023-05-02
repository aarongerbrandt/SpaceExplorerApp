package com.example.finalproject

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.finalproject.apod.Apod
import com.example.finalproject.rover.Rover
import com.example.finalproject.rover.RoverImages
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "NasaCaller"
class NasaCaller(context: Context) {
    private val APOD_URL = "https://api.nasa.gov/planetary/apod/"
    private val ROVER_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"

    private val nasaDateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.US)

    private val API_KEY = BuildConfig.API_KEY
    private val queue:RequestQueue
    private val gson = Gson()

    init {
        queue = Volley.newRequestQueue(context)
    }

    fun getApod(date:String, thumbnail:Boolean=false, callback: (response: Apod) -> Unit, error_callback: (exception: VolleyError) -> Unit) {
        val finalUrl = APOD_URL + "?date=${date}&thumbs=${thumbnail}&api_key=${API_KEY}"

        val jsonRequest = JsonObjectRequest(
            finalUrl,
            { response ->
                val apod = gson.fromJson(response.toString(), Apod::class.java)
                callback(apod)
            },
            { error ->
                error_callback(error)
            }
        )

        queue.add(jsonRequest)
    }

    fun getRover(date:Date, rover: String, callback: (response: List<Rover>) -> Unit) {
        val strDate = nasaDateFormat.format(date)
        val url = ROVER_URL + "${rover}/photos?earth_date=${strDate}&api_key=${API_KEY}"

        val jsonRequest = JsonObjectRequest(
            url,
            {   response ->
                val res = gson.fromJson(response.toString(), RoverImages::class.java)
                val rovers = ArrayList<Rover>()
                for (photo in res.photos) {
                    val roverObj = Rover (
                        response_id = 0,
                        image_id = photo.id,
                        sol = photo.sol,
                        img_src = photo.img_src,
                        earth_date = photo.earth_date,

                        camera_id = photo.camera.id,
                        camera_name = photo.camera.name,
                        camera_full_name = photo.camera.full_name,

                        rover_id = photo.rover.id,
                        rover_name = photo.rover.name,
                        rover_landing_date = photo.rover.landing_date,
                        rover_launch_date = photo.rover.launch_date,
                        rover_status = photo.rover.status
                    )

                    rovers += roverObj
                }

                callback(rovers)
            },
            { error ->
                Log.e(TAG, "Rover oopsy: $error")
            }
        )

        queue.add(jsonRequest)
    }
}