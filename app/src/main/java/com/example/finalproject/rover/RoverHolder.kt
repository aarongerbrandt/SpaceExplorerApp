package com.example.finalproject.rover

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ListItemRoverBinding
import com.example.finalproject.util.DateFormats

class RoverHolder(private val binding: ListItemRoverBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(rover: Rover) {
        binding.roverCamera.text = rover.camera_full_name
        binding.roverEarthDate.text = DateFormats.SIMPLE_OUTPUT_FORMAT.format(rover.earth_date)
        Glide.with(itemView.context)
            .load(rover.img_src)
            .override(RecyclerView.LayoutParams.MATCH_PARENT)
            .into(binding.roverImageView)

        binding.roverImageView.contentDescription = "Image from ${rover.camera_full_name}"

        // TODO: Add transition
        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${rover.camera_full_name} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.root.setOnLongClickListener {
            if(binding.roverImageView.visibility == View.GONE) {
                binding.roverImageView.visibility = View.VISIBLE
            } else {
                binding.roverImageView.visibility = View.GONE
            }

            true
        }
    }
}