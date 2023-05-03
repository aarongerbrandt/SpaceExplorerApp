package com.example.finalproject.rover

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ListItemRoverBinding
import java.text.SimpleDateFormat
import java.util.*

class RoverHolder(private val binding: ListItemRoverBinding): RecyclerView.ViewHolder(binding.root) {

    private val prettyDateFormat = SimpleDateFormat("EEE, MMM d, yy", Locale.US)

    fun bind(rover: Rover) {
        binding.roverCamera.text = rover.camera_name
        binding.roverEarthDate.text = prettyDateFormat.format(rover.earth_date)
        Glide.with(itemView.context)
            .load(rover.img_src)
            .override(RecyclerView.LayoutParams.MATCH_PARENT)
            .into(binding.roverImageView)

        binding.roverImageView.contentDescription = "Image from ${rover.camera_full_name}"

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${rover.camera_full_name} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.root.setOnLongClickListener {
            if(binding.roverCamera.visibility == View.GONE) { //TODO: Finish this
                binding.roverCamera.visibility = View.VISIBLE
                binding.roverEarthDate.visibility = View.VISIBLE
                binding.roverImageView.visibility = View.VISIBLE
            } else {
                binding.roverCamera.visibility = View.GONE
                binding.roverEarthDate.visibility = View.GONE
                binding.roverImageView.visibility = View.GONE
            }

            true
        }
    }
}