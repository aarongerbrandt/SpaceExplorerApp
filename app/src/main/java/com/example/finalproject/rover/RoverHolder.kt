package com.example.finalproject.rover

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ListItemRoverBinding
import java.text.SimpleDateFormat

class RoverHolder(private val binding: ListItemRoverBinding): RecyclerView.ViewHolder(binding.root) {

    private val prettyDateFormat = SimpleDateFormat("EEE, MMM d, yy")

    fun bind(rover: Rover) {
        binding.roverCamera.text = rover.camera_name
        binding.roverEarthDate.text = prettyDateFormat.format(rover.earth_date)
        Glide.with(itemView.context)
            .load(rover.img_src)
            .into(binding.roverImageView)

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${rover.camera_full_name} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}