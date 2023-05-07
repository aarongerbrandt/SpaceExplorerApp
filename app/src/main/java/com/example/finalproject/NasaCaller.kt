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
import com.example.finalproject.util.DateFormats
import com.example.finalproject.util.Urls
import com.google.gson.Gson
import java.util.Date

private const val TAG = "NasaCaller"
class NasaCaller(context: Context) {
    private val apiKey = BuildConfig.API_KEY
    private val queue:RequestQueue
    private val gson = Gson()

    init {
        queue = Volley.newRequestQueue(context)
    }

    fun getApod(date:Date, thumbnail:Boolean=false, callback: (response: Apod) -> Unit, error_callback: (exception: VolleyError) -> Unit) {
        val stringDate = DateFormats.NASA_FORMAT.format(date)
        val finalUrl = Urls.apodUrl + "?date=${stringDate}&thumbs=${thumbnail}&api_key=${apiKey}"

        val jsonRequest = JsonObjectRequest(
            finalUrl,
            { response ->
                val apod = gson.fromJson(response.toString(), Apod::class.java)
                Log.d("NasaCaller", "Got apod: $apod")
                callback(apod)
            },
            { error ->
                error_callback(error)
            }
        )

        queue.add(jsonRequest)
    }

    fun getRover(date:Date, rover: String, camera: String, callback: (response: List<Rover>) -> Unit) {
        val strDate = DateFormats.NASA_FORMAT.format(date)
        val url = Urls.apodUrl + "${rover}/photos?earth_date=${strDate}&camera=${camera}&api_key=${apiKey}"

        Log.d(TAG, "Rover URL: $url")

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
                //TODO: Implement Rover error handling
                Log.e(TAG, "Rover request Error: $error")
            }
        )

        queue.add(jsonRequest)
    }
}